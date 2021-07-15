package com.trungtamjava.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.UserService;

@RestController
public class RestUser {
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/user/add")
	public UserDTO addUser(@ModelAttribute UserDTO userDTO) {
		userService.insert(userDTO);
		return userDTO;

	}

	@GetMapping(value = "/user/search")
	public List<UserDTO> listUser(int start,int lenght) {
		List<UserDTO> list = userService.getAll(start,lenght);
		return list;
	}

	@GetMapping(value = "/user/get/{id}")
	public UserDTO get(@PathVariable(name = "id") int id) {
		return userService.get(id);
	}

	@PostMapping(value = "/user/update")
	public void updateUser(@RequestBody UserDTO userDTO) {
		userService.update(userDTO);
	}

	@GetMapping(value = "/user/delete/{id}")
	public void deleteUser(@PathVariable(name = "id") int id) {
		userService.delete(id);
	}
}
