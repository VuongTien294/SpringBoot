package com.trungtamjava.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.model.UserPrincipal;

@Controller
public class CommonWebController {

@GetMapping(value = "/")
public String index() {
	//Vao trang chu thi chuyen huong sang trang show products
	return "redirect:/products";
}

@GetMapping(value = "/admin/home")
public String AdminHome() {
	return "admin/home";
}


@GetMapping(value = "/member/home")
public String home(HttpServletRequest req) {
	UserPrincipal nowUserPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	if (nowUserPrincipal.getRole().equals("ROLE_ADMIN")) {
//		return "redirect:/admin/user/search";
		return "redirect:/admin/home";
	} else if(nowUserPrincipal.getRole().equals("ROLE_MEMBER")) {
        return "redirect:/products";
    }
	return "redirect:/member/profile";
	//Co 2 nhom thanh vien la admin va member(Xem cau hinh trong security).Neu la admin thi 
	//chuyen huong sang trang quan tri user con la member thi sang trang show sp
	
	
}
	
@GetMapping(value = "/download")
public void download(HttpServletResponse response, @RequestParam("image") String image) {
	final String uploadFolder = "D:\\test\\";
	File file = new File(uploadFolder + File.separator + image);
	if (file.exists()) {
		try {
			Files.copy(file.toPath(), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Tao file trong o D de download anh ve
}
}
