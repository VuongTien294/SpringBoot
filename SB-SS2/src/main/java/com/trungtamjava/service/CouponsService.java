package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.CouponsDTO;

public interface CouponsService {


	
	CouponsDTO getByName(String name);

	CouponsDTO get(int id);

	void delete(int id);

	void update(CouponsDTO couponsDTO);

	void insert(CouponsDTO couponsDTO);

	List<CouponsDTO> getAll(int start,int lenght);

	long countAll();

	long countSearch(String name);

//	CouponsDTO getByName(String name, int start, int lenght);

	List<CouponsDTO> getListByName(String name, int start, int lenght);

}
