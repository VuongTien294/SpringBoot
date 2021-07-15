package com.trungtamjava.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	public int id;
	public String name;
	public String image;
	public double price;
	public int quantity;
	public String description;
	public CategoryDTO categoryDTO;
	public MultipartFile file;// dung trong rest api

}
