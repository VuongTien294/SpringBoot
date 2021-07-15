package com.trungtamjava.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
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

import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.CommentDTO;
import com.trungtamjava.model.CouponsDTO;
import com.trungtamjava.model.MailDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.ReviewDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.model.UserPrincipal;
import com.trungtamjava.service.CategoryService;
import com.trungtamjava.service.CommentService;
import com.trungtamjava.service.CouponsService;
import com.trungtamjava.service.MailService;
import com.trungtamjava.service.ProductService;
import com.trungtamjava.service.ReviewService;

@Controller
public class ClientController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CouponsService couponsService;
	
	@Autowired
	private MailService mailService;

	@GetMapping(value = "/login")
	public String login(HttpServletRequest request,@RequestParam(name = "err",required = false) String error) {
		if (error!=null) {
			request.setAttribute("error", error);
		}
		return "client/login";
	}
	
	@GetMapping(value = "/register")
	public String register() {
		return "client/register";
	}
	
	@PostMapping(value = "/register")
	public String register(HttpServletRequest req,UserDTO userDTO) {
		userDTO.setEnabled(true);
		userDTO.setRole("ROLE_MEMBER");
		req.setAttribute("sucess", "Thanh Cong!");
		return "client/register";
	}
	
	@GetMapping(value = "/products")
	public String products(HttpServletRequest req,@RequestParam(name = "name",required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));
		List<CategoryDTO> listCate =categoryService.getAll(0, 30);
		if (name!=null&&!name.equals("null")) {
	        long count =productService.countSearch(name);
	        double result =Math.ceil((double) count / 8);
			List<ProductDTO> listProd =productService.search(name,(page-1)*8, 8);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
			req.setAttribute("listProduct", listProd);
			req.setAttribute("listCate", listCate);
		} else {
	        long count =productService.countSearch(name);
	        double result =Math.ceil((double) count / 8);
			List<ProductDTO> listProd =productService.getAll((page-1)*8, 8);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("listProduct", listProd);
			req.setAttribute("listCate", listCate);
		}

		return "client/product/searchProduct";
	}
	
	@GetMapping(value = "/product")
	public String product(HttpServletRequest req,@RequestParam(name = "id") int id) {
		ProductDTO productDTO =productService.get(id);
		List<ReviewDTO> listReviewDTOs =reviewService.find(id);
		List<CommentDTO> listCommentDTOs =commentService.searchByProduct(id);
		
		req.setAttribute("listReviewDTOs", listReviewDTOs);
		req.setAttribute("listCommentDTOs",listCommentDTOs);
		req.setAttribute("quantityReview",listReviewDTOs.size() );
		req.setAttribute("quantityComment",listCommentDTOs.size() );
		req.setAttribute("product", productDTO);
		return "client/product/detailProduct";
	}
	
	@GetMapping(value = "/products/searchbycate")
	public String searchByCate(HttpServletRequest req,@RequestParam(name = "cateId") int id) {
		List<CategoryDTO> listCategoryDTOs =categoryService.getAll(0, 30);
		List<ProductDTO> listProductDTOs =productService.searchByCateId(id);
		req.setAttribute("listCate", listCategoryDTOs);
		req.setAttribute("listProduct",listProductDTOs);
		
		return "client/product/searchProduct";
		
	}
	
	@GetMapping(value = "/add-to-cart")
	public String AddToCart(@RequestParam(name = "pid") int pId,HttpSession session) throws IOException {
		ProductDTO productDTO =productService.get(pId);
		Object object =session.getAttribute("cart");//Lay session neu co,con ko tao 1 session
        if (object==null) {//neu cart rong
			BillProductDTO billProductDTO =new BillProductDTO();//Tao moi 1 billproduct
			billProductDTO.setProduct(productDTO);//set product trong billProduct
			billProductDTO.setQuantity(1);//set sl =1
			billProductDTO.setUnitPrice(productDTO.getPrice());//set unitprice bang product price
			Map<Integer, BillProductDTO> map =new HashMap<Integer, BillProductDTO>();//Tao map de nhet billproduct vao
			map.put(pId, billProductDTO);//Vut billproduct vao map su dung pId cua product
			session.setAttribute("cart", map);//set lai session
		} else {
			Map<Integer, BillProductDTO> map =(Map<Integer, BillProductDTO>) object;//Lay map trong session ra(ep kieu doi tuong) 
            BillProductDTO billProductDTO =map.get(pId);//Lay billproduct trong map ra
            if (billProductDTO==null) {//Neu chua co billproduct trong map cua session
				billProductDTO =new BillProductDTO();//Tao 1 billproduct moi
				billProductDTO.setProduct(productDTO);//set sp trong billproduct moi nay
				billProductDTO.setQuantity(1);
				billProductDTO.setUnitPrice(productDTO.getPrice());
				map.put(pId, billProductDTO);//them billproduct vao map cua session
			} else {//neu co sp trong map roi thi tang sp len 1
            if (billProductDTO.getQuantity()<productDTO.getQuantity()) {
				billProductDTO.setQuantity(billProductDTO.getQuantity() + 1);
			} else {
                billProductDTO.setQuantity(billProductDTO.getQuantity());
			}
			}
            session.setAttribute("cart", map);
		}
        return "redirect:/cart";
	}
	@GetMapping(value = "/cart")
	public String cart(HttpServletRequest req,HttpSession session,Principal principal) {
		List<CategoryDTO> listCategoryDTOs =categoryService.getAll(0, 100);
		req.setAttribute("listCate", listCategoryDTOs);
		Object object =session.getAttribute("cart");
		long total = 0;
		if (object!=null) {
			Map<Integer, BillProductDTO> map=(Map<Integer, BillProductDTO>) object;
    		
    		for (Entry<Integer, BillProductDTO> entry : map.entrySet()) {
    			  BillProductDTO billProduct = entry.getValue();
    			  total += billProduct.getUnitPrice() * billProduct.getQuantity();
    		}
    		req.setAttribute("total", total);
    		return "client/cart/cart";
		} 
		
		req.setAttribute("total", total);
		return "client/cart/cart";
	}
	
	@PostMapping(value = "/cart")
	public String coupons(HttpSession session,HttpServletRequest req) {
		String couponsName =req.getParameter("coupons");
		CouponsDTO couponsDTO =couponsService.getByName(couponsName);
			
			if (couponsDTO!=null&&couponsDTO.getName().equals(couponsName)) {
				session.setAttribute("coupons",  couponsDTO);
				System.out.println("Phan Tram Giam Gia:"+ couponsDTO.getPersent()+"%");
				
			return "redirect:/cart";
		} 
		return "redirect:/cart";
	}
		
	@GetMapping(value = "/delete-from-cart")
	public String deleteFromCart(@RequestParam(name = "key") int key,HttpSession session) {
		Object object =session.getAttribute("cart");
		if (object!=null) {
        Map<Integer, BillProductDTO> map =(Map<Integer, BillProductDTO>) object;
        map.remove(key);
        session.setAttribute("cart", map);
		}
		return "redirect:/cart";
	}
	
	@GetMapping(value = "/client/about")
	public String aboutClient() {
		return "client/about";
	}
	
	@GetMapping(value = "/client/contact")
	public String contactClient() {
		return "client/contact";
	}
	
	@PostMapping(value = "/client/contact")
	public String contactClientPost(@RequestParam(name = "fname") String fname,@RequestParam(name = "lname") String lname,@RequestParam(name = "email") String email,@RequestParam(name = "subject") String subject
			,@RequestParam(name = "message") String message) {
	
	    new Thread() {
	    	public void run() {
	    		//send mail
			    try {
				    MailDTO mailDTO=new MailDTO();
				    mailDTO.setMailFrom("vuongtien2942000@gmail.com");
				    mailDTO.setMailTo("vuongtien10a4@gmail.com");
				    mailDTO.setMailSubject("Nguoi dung lien he :");
				    mailDTO.setMailContent(" Subject :"+subject +" .Message : "+message);
				    mailService.sendEmail(mailDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	};
	    }.start();
	    
	    new Thread() {
	    	public void run() {
	    		//send mail
			    try {
				    MailDTO mailDTO=new MailDTO();
				    mailDTO.setMailFrom("vuongtien2942000@gmail.com");
				    mailDTO.setMailTo("ptqnga3012@gmail.com");
				    mailDTO.setMailSubject("Thu phan hoi :");
				    mailDTO.setMailContent("Cam on ban da gop y cho shop !");
				    mailService.sendEmail(mailDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	};
	    }.start();
		
		return "client/contact";
	}
}
