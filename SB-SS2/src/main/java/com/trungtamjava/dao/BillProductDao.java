package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.BillProduct;

public interface BillProductDao {

	List<BillProduct> searchByBill(int id);

	List<BillProduct> searchByName(String name);

	BillProduct get(int id);

	void delete(BillProduct billProduct);

	void update(BillProduct billProduct);

	void insert(BillProduct billProduct);

}
