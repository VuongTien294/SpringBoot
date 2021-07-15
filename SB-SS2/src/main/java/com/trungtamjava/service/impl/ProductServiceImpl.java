package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trungtamjava.dao.ProductDao;
import com.trungtamjava.entity.Category;
import com.trungtamjava.entity.Product;
import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao productDao;

	@Override
	public void insert(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setImage(productDTO.getImage());
		product.setQuantity(productDTO.getQuantity());
		product.setDescription(productDTO.getDescription());

		Category category = new Category();
		category.setId(productDTO.getCategoryDTO().getId());
		category.setName(productDTO.getCategoryDTO().getName());

		product.setCategory(category);
		productDao.insert(product);
	}

	@Override
	public void update(ProductDTO productDTO) {
		Product product = productDao.get(productDTO.getId());
		if (product != null) {
			product.setName(productDTO.getName());
			product.setPrice(productDTO.getPrice());
			product.setImage(productDTO.getImage());
			product.setQuantity(productDTO.getQuantity());
			product.setDescription(productDTO.getDescription());

			Category category = new Category();
			category.setId(productDTO.getCategoryDTO().getId());
			category.setName(productDTO.getCategoryDTO().getName());

			product.setCategory(category);
			productDao.update(product);
		}
	}

	@Override
	public void delete(int id) {
		Product product = productDao.get(id);
		if (product != null) {
			productDao.delete(id);
		}
	}

	@Override
	public ProductDTO get(int id) {
		Product product = productDao.get(id);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setImage(product.getImage());
		productDTO.setPrice(product.getPrice());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setDescription(product.getDescription());

		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(product.getCategory().getId());
		categoryDTO.setName(product.getCategory().getName());

		productDTO.setCategoryDTO(categoryDTO);
		return productDTO;
	}

	@Override
	public List<ProductDTO> search(String name,int start,int lenght) {
		List<Product> listProducts = productDao.getByName(name,start,lenght);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			ProductDTO dto = new ProductDTO();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setImage(product.getImage());
			dto.setPrice(product.getPrice());
			dto.setQuantity(product.getQuantity());
			dto.setDescription(product.getDescription());

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(product.getCategory().getId());
			categoryDTO.setName(product.getCategory().getName());

			dto.setCategoryDTO(categoryDTO);
			productDTOs.add(dto);
		}
		return productDTOs;
	}
	
	@Override
	public List<ProductDTO> getAll(int start ,int length) {
		List<Product> listProducts=productDao.getAll(start ,length);
		List<ProductDTO> listProductDTOs=new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			ProductDTO dto = new ProductDTO();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setImage(product.getImage());
			dto.setPrice(product.getPrice());
			dto.setQuantity(product.getQuantity());
			dto.setDescription(product.getDescription());

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(product.getCategory().getId());
			categoryDTO.setName(product.getCategory().getName());

			dto.setCategoryDTO(categoryDTO);
			listProductDTOs.add(dto);
		}
		return listProductDTOs;
	}
	
	@Override
	public long countGetAll() {
		long count = productDao.countGetAll();
		return count;
	}

	
	@Override
	public long countSearch(String name) {
		long count = productDao.countSearch(name);
		return count;
	}
	
	@Override
	public List<ProductDTO> searchByCateId(int cateId){
		List<Product> listProduct =productDao.getByCate(cateId);
		List<ProductDTO> listProductDTOs= new ArrayList<ProductDTO>();
		for (Product product : listProduct) {
			ProductDTO productDTO=new ProductDTO();
			productDTO.setId(product.getId());
			productDTO.setImage(product.getImage());
			productDTO.setDescription(product.getDescription());
			productDTO.setName(product.getName());
			productDTO.setPrice(product.getPrice());
			productDTO.setQuantity(product.getQuantity());
			
			CategoryDTO categoryDTO =new CategoryDTO();
			categoryDTO.setId(product.getCategory().getId());
			categoryDTO.setName(product.getCategory().getName());
			
			productDTO.setCategoryDTO(categoryDTO);
			listProductDTOs.add(productDTO);
		}
		return listProductDTOs;
		
	}
	

}
