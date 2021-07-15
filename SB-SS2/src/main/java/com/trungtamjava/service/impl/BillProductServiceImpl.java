package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trungtamjava.dao.BillProductDao;
import com.trungtamjava.entity.Bill;
import com.trungtamjava.entity.BillProduct;
import com.trungtamjava.entity.Product;
import com.trungtamjava.model.BillDTO;
import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.service.BillProductService;

@Service
@Transactional
public class BillProductServiceImpl implements BillProductService {
@Autowired
private BillProductDao billProductDao;

@Override
public void insert(BillProductDTO billProductDTO) {
	BillProduct billProduct =new BillProduct();
	billProduct.setQuantity(billProductDTO.getQuantity());
	billProduct.setUnitPrice(billProductDTO.getUnitPrice());
	
	Product product =new Product();
	product.setId(billProductDTO.getProduct().getId());
	
	Bill bill =new Bill();
	bill.setId(billProductDTO.getBill().getId());
	
	billProduct.setProduct(product);
	billProduct.setBill(bill);
	
	billProductDao.insert(billProduct);
	
}

@Override
public void update(BillProductDTO billProductDTO) {
	BillProduct billProduct=new BillProduct();
	billProduct.setQuantity(billProductDTO.getQuantity());
	billProduct.setUnitPrice(billProductDTO.getUnitPrice());
	
	Product product =new Product();
	product.setId(billProductDTO.getProduct().getId());
	
	Bill bill=new Bill();
	bill.setId(billProductDTO.getBill().getId());
	
	billProductDao.update(billProduct);
}

@Override
public void delete(int id) {
	BillProduct billProduct =billProductDao.get(id);
	if(billProduct!=null) {
		billProductDao.delete(billProduct);
	}
	
}

@Override
public List<BillProductDTO> searchByName(String name){
	List<BillProduct> list = billProductDao.searchByName(name);
	List<BillProductDTO> list2 =new ArrayList<BillProductDTO>();
    for (BillProduct billProduct : list) {
		list2.add(convert(billProduct));
	}
    return list2;
}

@Override
public List<BillProductDTO> searchByBillId(int id){
	List<BillProduct> list = billProductDao.searchByBill(id);
	List<BillProductDTO> list2 =new ArrayList<BillProductDTO>();
    for (BillProduct billProduct : list) {
		list2.add(convert(billProduct));
	}
    return list2;
}

private BillProductDTO convert(BillProduct billProduct) {
	BillProductDTO billProductDTO =new BillProductDTO();
	billProductDTO.setId(billProduct.getId());
	billProductDTO.setQuantity(billProduct.getQuantity());
	billProductDTO.setUnitPrice(billProduct.getUnitPrice());
	
	ProductDTO productDTO =new ProductDTO();
	productDTO.setId(billProduct.getId());
	productDTO.setName(billProduct.getProduct().getName());
	productDTO.setImage(billProduct.getProduct().getImage());
	productDTO.setPrice(billProduct.getProduct().getPrice());
	
	BillDTO billDTO =new BillDTO();
	billDTO.setId(billProduct.getBill().getId());
	
	billProductDTO.setProduct(productDTO);
	billProductDTO.setBill(billDTO);
	
	return billProductDTO;
}

}
