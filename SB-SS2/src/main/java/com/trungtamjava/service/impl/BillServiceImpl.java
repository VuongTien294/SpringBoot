package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trungtamjava.dao.BillDao;
import com.trungtamjava.entity.Bill;
import com.trungtamjava.entity.User;
import com.trungtamjava.model.BillDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.BillService;
import com.trungtamjava.ultil.DateTimeUtils;

@Service
@Transactional
public class BillServiceImpl implements BillService {
	@Autowired
	BillDao billDao;

	@Override
	public void insert(BillDTO billDTO) {
		Bill bill = new Bill();
		bill.setBuyDate(new Date());
		
   	    User user =new User();
   	    user.setId(billDTO.getBuyer().getId());
   	    user.setName(billDTO.getBuyer().getName()); 	    
		bill.setBuyer(user);
		
		bill.setStatus(billDTO.getStatus());
		bill.setDiscountPercent(billDTO.getDiscountPercent());
		bill.setCouponsName(billDTO.getCouponsName());
		bill.setPay(billDTO.getPay());
				

		billDao.insert(bill);
		billDTO.setId(bill.getId());
	}

	@Override
	public void update(BillDTO billDTO) {
		Bill bill = billDao.get(billDTO.getId());
		if (bill != null) {
			bill.setPay(billDTO.getPay());
			bill.setDiscountPercent(billDTO.getDiscountPercent());
			bill.setCouponsName(billDTO.getCouponsName());
			bill.setPriceTotal(billDTO.getPriceTotal());
			bill.setStatus(billDTO.getStatus());
			billDao.update(bill);
		}

	}

	@Override
	public void delete(int id) {
		Bill bill = billDao.get(id);
		if (bill != null) {
			billDao.delete(bill);
		}
	}

	@Override
	public BillDTO get(int id) {
		Bill bill = billDao.get(id);
		return convertDTO(bill);
	}

	@Override
	public List<BillDTO> search(String name,int start,int lenght) {
		List<Bill> bills = billDao.searchByName(name,start,lenght);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		for (Bill bill : bills) {
			billDTOs.add(convertDTO(bill));
		}
		return billDTOs;
	}
	
	@Override
	public List<BillDTO> getAll(String name,int start,int lenght) {
		List<Bill> bills = billDao.getAll(name,start,lenght);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		for (Bill bill : bills) {
			billDTOs.add(convertDTO(bill));
		}
		return billDTOs;
	}
	
	@Override
	public long countSearch(String name) {
		long count =billDao.countSearch(name);
		return count;
	}
	
	@Override
	public long countAll() {
		long count =billDao.countAll();
		return count;
	}
	
	

	@Override
	public List<BillDTO> searchByBuyerId(int buyerId) {
		List<Bill> bills = billDao.searchByBuyerId(buyerId);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		if (bills.isEmpty()) {
			return null;
		} else {
			for (Bill bill : bills) {
				billDTOs.add(convertDTO(bill));
			}
			return billDTOs;
		}

	}

	private BillDTO convertDTO(Bill bill) {
		BillDTO billDTO = new BillDTO();
		billDTO.setId(bill.getId());
		billDTO.setStatus(bill.getStatus());
		billDTO.setBuyDate(DateTimeUtils.formatDate(bill.getBuyDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
		billDTO.setPriceTotal(bill.getPriceTotal());
		billDTO.setDiscountPercent(bill.getDiscountPercent());
		billDTO.setPay(bill.getPay());
		billDTO.setCouponsName(bill.getCouponsName());
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(bill.getBuyer().getId());
		userDTO.setName(bill.getBuyer().getName());
		billDTO.setBuyer(userDTO);

		return billDTO;
	}
}
