package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Coupons;

public interface CouponsDao {

//	Coupons getByName(String name,int start,int lenght);
	
	Coupons getByName(String name);

	Coupons get(int id);

	void delete(int id);

	void update(Coupons coupons);

	void insert(Coupons coupons);

	List<Coupons> getAll(int start,int lenght);

	long countAll();

	long countSearch(String name);

	List<Coupons> getListByName(String name, int start, int lenght);

}
