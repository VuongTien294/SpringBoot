package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.BillDTO;

public interface BillService {

	List<BillDTO> searchByBuyerId(int buyerId);

	List<BillDTO> search(String name,int start,int lenght);

	BillDTO get(int id);

	void delete(int id);

	void update(BillDTO billDTO);

	void insert(BillDTO billDTO);

	long countAll();

	long countSearch(String name);

	List<BillDTO> getAll(String name,int start, int lenght);

}
