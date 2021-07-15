package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.User;

public interface UserDao {

	List<User> getAll(int start,int lenght);

	User get(int id);

	void delete(int id);

	void update(User user);

	void insert(User user);

	List<User> getByName(String name,int start,int length);

	User getByUserName(String username);

	long countGetAll();

	long countSearch(String name);

}
