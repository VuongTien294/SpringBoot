package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Bill;

public interface BillDao {

	List<Bill> searchByBuyerId(int id);

	List<Bill> searchByName(String name,int start,int lenght);

	Bill get(int id);

	void delete(Bill bill);

	void update(Bill bill);

	void insert(Bill bill);

	long countAll();

	long countSearch(String name);

	List<Bill> getAll(String name,int start, int lenght);

}
