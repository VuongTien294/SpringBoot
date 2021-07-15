package com.trungtamjava.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.CouponsDTO;
import com.trungtamjava.service.CouponsService;

@Controller
public class CouponController {

	@Autowired
	private CouponsService couponService;

	@GetMapping(value = "/admin/coupon/add")
	public String add() {
		return "admin/coupon/addCoupon";
	}

	@PostMapping(value = "/admin/coupon/add")
	public String add(HttpServletRequest req, @ModelAttribute CouponsDTO couponsDTO) {
		couponService.insert(couponsDTO);
		return "admin/coupon/addCoupon";
	}

	@GetMapping(value = "/admin/coupon/search")
	public String listCoupon(HttpServletRequest req, @RequestParam(name = "name", required = false) String name) {

		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));
		if (name != null && !name.equals("null")) {
			long count = couponService.countSearch(name);
			double result = Math.ceil((double) count / 5);
			List<CouponsDTO> list = couponService.getListByName(name, (page-1)*5, 5);
			req.setAttribute("listCoupon", list);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
		} else {
			long count = couponService.countAll();
			double result = Math.ceil((double) count / 5);
			List<CouponsDTO> list = couponService.getAll((page-1)*5, 5);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("listCoupon", list);
		}

		return "admin/coupon/listCoupon";
	}

	@GetMapping(value = "/admin/coupon/delete")
	public String deleteCoupon(@RequestParam(name = "id") int id) {
		couponService.delete(id);
		return "redirect:/admin/coupon/search";
	}

	@GetMapping(value = "/admin/coupon/update")
	public String updateCoupon(@RequestParam(name = "id") int id, Model model) {
		CouponsDTO couponsDTO = couponService.get(id);
		model.addAttribute("coupon", couponsDTO);
		return "admin/coupon/updateCoupon";
	}

	@PostMapping(value = "/admin/coupon/update")
	public String updateCoupon(@ModelAttribute(name = "coupon") CouponsDTO couponsDTO) {
		couponService.update(couponsDTO);
		return "redirect:/admin/coupon/search";
	}
}
