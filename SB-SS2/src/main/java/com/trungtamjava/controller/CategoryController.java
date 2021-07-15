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

import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.service.CategoryService;

@Controller
public class CategoryController {
@Autowired
CategoryService categoryService;
@GetMapping(value = "/admin/category/add")
public String add() {
	return "admin/category/addCategory";
}
@PostMapping(value = "/admin/category/add")
public String add(CategoryDTO categoryDTO,HttpServletRequest req) {
	categoryService.insert(categoryDTO);
	req.setAttribute("sucess", "Thanh Cong!");
	return "admin/category/addCategory";
}
@GetMapping(value = "/admin/category/update")
public String update(Model model,@RequestParam(name = "id") int id) {
	CategoryDTO categoryDTO =categoryService.get(id);
	model.addAttribute("category", categoryDTO);
	return "admin/category/updateCategory";
}
@PostMapping(value = "/admin/category/update")
	public String update(@ModelAttribute(name = "category") CategoryDTO categoryDTO) {
	categoryService.update(categoryDTO);
	return "redirect:/admin/category/search";
}
@GetMapping(value = "/admin/category/delete")
public String delete(@RequestParam(name = "id") int id) {
	categoryService.delete(id);
	return "redirect:/admin/category/search";
}

@GetMapping(value = "/admin/category/search")
public String search(HttpServletRequest req,@RequestParam(name = "name",required = false) String name) {
	Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));
	if (name!=null&&!name.equals("null")) {
		long count = categoryService.countSearch(name);
		double result =Math.ceil((double)count/6);
		List<CategoryDTO> list =categoryService.getByName(name, (page-1)*6, 6);
		req.setAttribute("currentPage", page);
		req.setAttribute("result", result);
		req.setAttribute("name", name);
		req.setAttribute("cateList", list);
	} else {
		long count = categoryService.countAll();
		double result =Math.ceil((double)count/6);
		List<CategoryDTO> list =categoryService.getAll((page-1)*6, 6);
		req.setAttribute("currentPage", page);
		req.setAttribute("result", result);
		req.setAttribute("cateList", list);
	}

	return "admin/category/listCategory";
}



}
