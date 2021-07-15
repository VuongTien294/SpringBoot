package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.BillProductDTO;

public interface BillProductService {

	List<BillProductDTO> searchByBillId(int id);

	List<BillProductDTO> searchByName(String name);

	void delete(int id);

	void update(BillProductDTO billProductDTO);

	void insert(BillProductDTO billProductDTO);

}
