package com.trungtamjava.controller;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.model.BillDTO;
import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.model.CommentDTO;
import com.trungtamjava.model.CouponsDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.ReviewDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.model.UserPrincipal;
import com.trungtamjava.service.BillProductService;
import com.trungtamjava.service.BillService;
import com.trungtamjava.service.CommentService;
import com.trungtamjava.service.ProductService;
import com.trungtamjava.service.ReviewService;
import com.trungtamjava.service.UserService;
import com.trungtamjava.service.MailService;
import com.trungtamjava.model.MailDTO;

@Controller
public class MemberController {
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ReviewService reviewService;
     
	@Autowired
	private MailService mailService;

	@Autowired
	private UserService userService;

	@Autowired
	private BillProductService billProductService;

	@Autowired
	private BillService billService;

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/member/bill/add")
	public String addOrder(HttpServletRequest req, HttpSession session) {
		
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDTO userDTO = new UserDTO();
		userDTO.setId(userPrincipal.getId());// Lay nguoi dung hien tai

		Object object = session.getAttribute("cart");

		if (object != null) {// Neu session cart ton tai
			Map<String, BillProductDTO> map = (Map<String, BillProductDTO>) object;// Khai bao map roi ap kieu map cho
																					// session cua cart

			BillDTO bill = new BillDTO();// Tao bill moi
			bill.setBuyer(userDTO);// Set nguoi mua cho bill la nguoi dung hien tai
			bill.setStatus("NEW");
			billService.insert(bill);

			double totalPrice = 0;
			for (Entry<String, BillProductDTO> entry : map.entrySet()) {// duyet map de lay billproduct ra
				BillProductDTO billProduct = entry.getValue();
				billProduct.setBill(bill);
				billProductService.insert(billProduct); // bill

				totalPrice = totalPrice + (billProduct.getQuantity() * billProduct.getUnitPrice()) + 30;// Tinh tong gia

				ProductDTO productDTO = productService.get(entry.getValue().getProduct().getId());
				productDTO.setQuantity(productDTO.getQuantity() - billProduct.getQuantity());// Tru san pham con trong
																								// kho khi mua
				productService.update(productDTO);
				
				//Xet coupons
				double finalTotalPrice = 0;
				CouponsDTO couponsDTO = (CouponsDTO) session.getAttribute("coupons");
				if (couponsDTO != null) {				
					finalTotalPrice =30+ (billProduct.getQuantity() * billProduct.getUnitPrice()) - (((billProduct.getQuantity() * billProduct.getUnitPrice()) * couponsDTO.getPersent()) / 100);
					bill.setPriceTotal(finalTotalPrice);
					bill.setDiscountPercent(couponsDTO.getPersent());
					System.out.println("Phan tram giam :"+couponsDTO.getPersent());
					bill.setCouponsName(couponsDTO.getName());
					System.out.println("Ten Coupons :"+couponsDTO.getName());
					bill.setPay(String.valueOf(totalPrice));
					
					System.out.println("FinalTotal Price :" + finalTotalPrice);
				}else {
					bill.setPriceTotal(totalPrice-30);
					bill.setDiscountPercent(0);
					bill.setPay(String.valueOf(totalPrice-30));
					bill.setCouponsName("No discount");

				}
							
			}

			billService.update(bill);// update lai bill sau khi da tinh xong
			
		    new Thread() {
		    	public void run() {
		    		//send mail
				    try {
					    MailDTO mailDTO=new MailDTO();
					    mailDTO.setMailFrom("vuongtien2942000@gmail.com");
					    mailDTO.setMailTo(userPrincipal.getEmail());
					    mailDTO.setMailSubject("Shop Giay!");
					    mailDTO.setMailContent("Ban da dat thanh cong don hang o cua hang giay cua TienDepTrai1234.Cam on "+userPrincipal.getName());
					    mailService.sendEmail(mailDTO);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    	};
		    }.start();
			
			session.removeAttribute("cart");
			session.removeAttribute("coupons");
			return "client/cart/order-complete";
		}
		return "redirect:/products";
	}
	
	@PostMapping(value = "/member/comment")
	public String memberComment(HttpServletRequest req,@RequestParam(name = "Pid") int Pid,
			@RequestParam(name = "comment") String comment) {
		UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CommentDTO commentDTO =new CommentDTO();
		commentDTO.setContent(comment);
		
		UserDTO userDTO =new UserDTO();
		userDTO.setId(principal.getId());
		
		ProductDTO productDTO =new ProductDTO();
		productDTO.setId(Pid);
		
		commentDTO.setProductDTO(productDTO);
		commentDTO.setUserDTO(userDTO);
//		commentDTO.setCreatedDate(new Date());
		commentService.add(commentDTO);
		System.out.println("Comment:"+comment);
		return "redirect:/product?id="+Pid;
	}
	
	@PostMapping(value = "/member/review")
	public String memberReview(HttpServletRequest req,@RequestParam(name = "Pid") int Pid
			,@RequestParam(name = "rating") String rating) {
		UserPrincipal principal =(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ReviewDTO reviewDTO =new ReviewDTO();
		reviewDTO.setStarNumber(Integer.parseInt(rating));
		
		UserDTO userDTO =new UserDTO();
		userDTO.setId(principal.getId());
		
		ProductDTO productDTO =new ProductDTO();
		productDTO.setId(Pid);
		
		reviewDTO.setUserDTO(userDTO);
		reviewDTO.setProductDTO(productDTO);
		
		reviewService.add(reviewDTO);
		System.out.println("Review:" +rating);
		
		return "redirect:/product?id="+Pid;
	}
}
