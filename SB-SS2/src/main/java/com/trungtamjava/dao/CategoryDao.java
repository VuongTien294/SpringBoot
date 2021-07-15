package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Category;

public interface CategoryDao {

	List<Category> getByName(String name,int start,int lenght);

	Category get(int id);

	void delete(int id);

	void update(Category category);

	void insert(Category category);

	long countGetAll();

	long countSearch(String name);

	List<Category> getAll(int start, int lenght);

}
