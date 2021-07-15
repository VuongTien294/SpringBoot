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

import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/admin/user/add")
	public String add() {
		return "admin/user/addUser";
	}

	@PostMapping(value = "/admin/user/add")
	public String add(UserDTO userDTO, HttpServletRequest req) {
		userDTO.setEnabled(true);
		userService.insert(userDTO);
		req.setAttribute("sucess", "Thanh Cong!");
		return "admin/user/addUser";

	}

	@GetMapping(value = "/admin/user/update")
	public String update(Model model, @RequestParam(name = "id") int id) {
		UserDTO user = userService.get(id);
		model.addAttribute("user", user);
		return "admin/user/updateUser";
	}

	@PostMapping(value = "/admin/user/update")
	public String update(@ModelAttribute(name = "user") UserDTO user) {
		userService.update(user);
		return "redirect:/admin/user/search";
	}

	@GetMapping(value = "/admin/user/search")
	public String search(HttpServletRequest req, @RequestParam(name = "name", required = false) String name) {
		Integer page = req.getParameter("page") == null ? 1 : Integer.valueOf(req.getParameter("page"));

		if (name != null && !name.equals("null")) {
			long count = userService.countSearch(name);
			double result = Math.ceil((double) count / 5);
			List<UserDTO> list = userService.getByName(name, (page - 1) * 5, 5);

			req.setAttribute("userList", list);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			req.setAttribute("name", name);
		} else {
            long count =userService.countGetAll();
            double result =Math.ceil((double) count / 5);
			List<UserDTO> list = userService.getAll((page - 1) * 5, 5);
			req.setAttribute("userList", list);
			req.setAttribute("currentPage", page);
			req.setAttribute("result", result);
			
            
		}
		
		return "admin/user/listUser";
	}

//	@PostMapping(value = "/admin/user/search")
//	public String search(@RequestParam(name = "name") String name,HttpServletRequest req) {
//        List<UserDTO> list = userService.getByName(name);
//        req.setAttribute("userList", list);
//		return "admin/user/listUser";		
//	}

	@GetMapping(value = "/admin/user/delete")
	public String delete(@RequestParam(name = "id") int id) {
		userService.delete(id);
		return "redirect:/admin/user/search";
	}
}
