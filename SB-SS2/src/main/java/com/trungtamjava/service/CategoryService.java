package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.CategoryDTO;

public interface CategoryService {

	List<CategoryDTO> getByName(String name,int start,int lenght);

	CategoryDTO get(int id);

	void delete(int id);

	void update(CategoryDTO categoryDTO);

	void insert(CategoryDTO categoryDTO);

	long countAll();

	long countSearch(String name);

	List<CategoryDTO> getAll(int start, int lenght);

}
