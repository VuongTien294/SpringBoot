package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.ProductDTO;

public interface ProductService {

	List<ProductDTO> search(String name,int start,int lenght);

	ProductDTO get(int id);

	void delete(int id);

	void update(ProductDTO productDTO);

	void insert(ProductDTO productDTO);

	List<ProductDTO> searchByCateId(int cateId);

	long countSearch(String name);

	long countGetAll();

	List<ProductDTO> getAll(int start, int length);

}
