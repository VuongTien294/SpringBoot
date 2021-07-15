package com.trungtamjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillProductDTO {
	public int id;
	public double unitPrice;
	public int quantity;
	public BillDTO bill;
	public ProductDTO product;
//	public String productName;
	
}
