package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Product;

public interface ProductDao {

	List<Product> getByName(String name,int start,int lenght);

	Product get(int id);

	void delete(int id);

	void update(Product product);

	void insert(Product product);

	List<Product> getByCate(int cateId);

	long countSearch(String name);

	long countGetAll();

	List<Product> getAll(int start, int length);

}
