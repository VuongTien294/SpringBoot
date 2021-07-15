package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.UserDTO;

public interface UserService {

	List<UserDTO> getAll(int start,int lenght);

	UserDTO get(int id);

	void delete(int id);

	void update(UserDTO userDTO);

	void insert(UserDTO userDTO);

	List<UserDTO> getByName(String name,int start,int length);

	void updateProfile(UserDTO userDTO);

	void setupUserPassword(UserDTO userDTO);

	void resetPassword(UserDTO accountDTO);

	void changePassword(UserDTO userDTO);

	long countSearch(String name);

	long countGetAll();



}
