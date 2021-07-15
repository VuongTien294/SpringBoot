package com.trungtamjava.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.service.CategoryService;
import com.trungtamjava.service.ProductService;

@Controller
public class ProductController {
@Autowired
private ProductService productService;

@Autowired
private CategoryService categoryService;

@GetMapping(value = "/admin/product/add")
public String add(HttpServletRequest req,Model model) {
	List<CategoryDTO> list =categoryService.getAll(0, 30);
	model.addAttribute("product", new ProductDTO());
	req.setAttribute("categoryList", list);
	return "admin/product/addProduct";
}
@PostMapping(value = "/admin/product/add")
public String add(HttpServletRequest req,@ModelAttribute(name ="product" ) ProductDTO productDTO,
		@RequestParam(name = "imageFile") MultipartFile imagefile) {
	
	String originalFilename = imagefile.getOriginalFilename();
	int lastIndex = originalFilename.lastIndexOf(".");
	String ext = originalFilename.substring(lastIndex);

	String avatarFilename = System.currentTimeMillis() + ext;
	File newfile = new File("D:\\test\\" + avatarFilename);
	FileOutputStream fileOutputStream;
	try {
		fileOutputStream = new FileOutputStream(newfile);
		fileOutputStream.write(imagefile.getBytes());
		fileOutputStream.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	productDTO.setImage(avatarFilename);
	
	productService.insert(productDTO);
	req.setAttribute("sucess", "Thanh Cong!");
	return "admin/product/addProduct";
}
@GetMapping(value = "/admin/product/search")
public String search(HttpServletRequest req,@RequestParam(name = "name",required = false) String name) {
	Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));
	if (name!=null&&!name.equals("null")) {
        long count =productService.countSearch(name);
        double result =Math.ceil((double) count / 5);
		List<ProductDTO> listProductDTOs =productService.search(name, (page-1)*6, 6);
		req.setAttribute("productList", listProductDTOs);
		req.setAttribute("currentPage", page);
		req.setAttribute("result", result);
		req.setAttribute("name", name);
	} else {
        long count =productService.countGetAll();
        double result =Math.ceil((double) count / 5);
		List<ProductDTO> listProductDTOs =productService.getAll((page-1)*6, 6);
		req.setAttribute("productList", listProductDTOs);
		req.setAttribute("currentPage", page);
		req.setAttribute("result", result);		
	}
	return "admin/product/listProduct";
}

@GetMapping(value = "/admin/product/update")
public String update(HttpServletRequest req,@RequestParam(name = "id") int id,Model model) {
	ProductDTO productDTO =productService.get(id);
	List<CategoryDTO> categoryDTOs =categoryService.getAll(30,30);
	req.setAttribute("categoryList", categoryDTOs);
	model.addAttribute("product", productDTO);
	return "admin/product/updateProduct";
}
@PostMapping(value = "/admin/product/update")
public String update(@ModelAttribute(name = "product") ProductDTO productDTO,
		@RequestParam(name = "imageFile") MultipartFile imagefile) {
	
	String originalFilename = imagefile.getOriginalFilename();
	int lastIndex = originalFilename.lastIndexOf(".");
	String ext = originalFilename.substring(lastIndex);

	String avatarFilename = System.currentTimeMillis() + ext;
	File newfile = new File("D:\\test\\" + avatarFilename);
	FileOutputStream fileOutputStream;
	try {
		fileOutputStream = new FileOutputStream(newfile);
		fileOutputStream.write(imagefile.getBytes());
		fileOutputStream.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	productDTO.setImage(avatarFilename);
	productService.update(productDTO);
	return "redirect:/admin/product/search";
}
@GetMapping(value = "/admin/product/delete")
public String delete(@RequestParam(name = "id") int id) {
	productService.delete(id);
	return "redirect:/admin/product/search";
}

}
