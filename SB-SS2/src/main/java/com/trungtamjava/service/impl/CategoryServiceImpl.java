package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trungtamjava.dao.CategoryDao;
import com.trungtamjava.entity.Category;
import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao categoryDao;

	@Override
	public void insert(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());
		categoryDao.insert(category);
	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		Category category = categoryDao.get(categoryDTO.getId());
		if (category != null) {
			category.setName(categoryDTO.getName());
			categoryDao.update(category);
		}
	}

	@Override
	public void delete(int id) {
		Category category = categoryDao.get(id);
		if (category != null) {
			categoryDao.delete(id);			
		}
	}

	@Override
	public CategoryDTO get(int id) {
		Category category = categoryDao.get(id);
		return convert(category);
	}

	@Override
	public List<CategoryDTO> getByName(String name,int start,int lenght) {
		List<Category> list = categoryDao.getByName(name,start,lenght);
		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
		for (Category category : list) {
			categoryDTOs.add(convert(category));
		}
		return categoryDTOs;
	}
	
	@Override
	public List<CategoryDTO> getAll(int start,int lenght) {
		List<Category> list = categoryDao.getAll(start,lenght);
		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
		for (Category category : list) {
			categoryDTOs.add(convert(category));
		}
		return categoryDTOs;
	}
	
	@Override
	public long countSearch(String name) {
		long count =categoryDao.countSearch(name);
		return count;
	}
	
	@Override
	public long countAll() {
		long count =categoryDao.countGetAll();
		return count;
	}

	private CategoryDTO convert(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		return categoryDTO;
	}
}
