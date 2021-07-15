package com.trungtamjava.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.trungtamjava.model.BillDTO;
import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.BillProductService;
import com.trungtamjava.service.BillService;
import com.trungtamjava.service.ProductService;
import com.trungtamjava.service.UserService;

@Controller
public class BillController {
	@Autowired
	private BillService billService;

	@Autowired
	private UserService userService;

	@Autowired
	private BillProductService billProductService;
	
	@Autowired
	private ProductService productService;

	@GetMapping(value = "/admin/bill/add")
	public String addBill(HttpServletRequest req,Model model,@RequestParam(name = "name",required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));
		List<UserDTO> list = userService.getAll(0, 30);
//		List<UserDTO> list = userService.getByName("", , length);
		model.addAttribute("bill", new BillDTO());
		req.setAttribute("userList", list);
		return "admin/bill/addBill";
	}

	@PostMapping(value = "/admin/bill/add")
	public String addBill(HttpServletRequest request, @ModelAttribute(name = "bill") BillDTO billDTO) {
		billService.insert(billDTO);
		request.setAttribute("sucess", "Thanh Cong!");
		return "redirect:/admin/bill/add";
	}
	
	@GetMapping(value = "/admin/billproduct/add")
	public String addBillPro(HttpServletRequest req,Model model,@RequestParam(name = "billId") int id) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));
		List<ProductDTO> list =productService.getAll((page-1)*5,5);
		req.setAttribute("productList", list);
		model.addAttribute("billproduct", new BillProductDTO());
		return "admin/billproduct/addBillProduct";
	}
	
	@PostMapping(value = "/admin/billproduct/add")
	public String addBillPro(@ModelAttribute(name = "billproduct") BillProductDTO billProductDTO) {
		billProductService.insert(billProductDTO);
		return "admin/billproduct/listBillProduct";
	}

	@GetMapping(value = "/admin/bill/search")
	public String search(HttpServletRequest req,@RequestParam(name = "name",required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));
		if (name!=null&&!name.equals("null")) {
	        long count =billService.countSearch(name);
	        double result =Math.ceil((double) count / 5);
			List<BillDTO> list = billService.search(name, (page-1)*5, 5);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
			req.setAttribute("billList", list);
		} else {
	        long count =billService.countAll();
	        double result =Math.ceil((double) count / 5);
			List<BillDTO> list = billService.getAll("",(page-1)*5, 5);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("billList", list);
		}

		return "admin/bill/listBill";
	}

	@GetMapping(value = "/admin/billproduct/search")
	public String searchBillPro(HttpServletRequest req, @RequestParam(name = "billId") int id) {
		List<BillProductDTO> list = billProductService.searchByBillId(id);
		req.setAttribute("billproductList", list);
		return "admin/billproduct/listBillProduct";
	}

	@GetMapping(value = "/admin/bill/delete")
	public String deleteBill(@RequestParam(name = "billId") int id, HttpServletRequest req) {
		billService.delete(id);
		return "redirect:/admin/bill/search";
	}

	@GetMapping(value = "/admin/billproduct/delete")
	public String deleteBillProduct(@RequestParam(name = "billProId") int id) {
		billProductService.delete(id);
		return "redirect:/admin/billproduct/search";
	}
}
