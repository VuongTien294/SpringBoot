package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trungtamjava.dao.CouponsDao;
import com.trungtamjava.entity.Coupons;
import com.trungtamjava.model.CouponsDTO;
import com.trungtamjava.service.CouponsService;

@Service
@Transactional
public class CouponsServiceImpl implements CouponsService {
	@Autowired
	private CouponsDao couponsDao;
	
	@Override
	public void insert(CouponsDTO couponsDTO) {
		Coupons coupons =new Coupons();
		coupons.setName(couponsDTO.getName());
		coupons.setPersent(couponsDTO.getPersent());
		couponsDao.insert(coupons);
	}
	
	@Override
	public void update(CouponsDTO couponsDTO) {
		Coupons coupons =couponsDao.get(couponsDTO.getId());
		if (coupons!=null) {
			coupons.setName(couponsDTO.getName());
			coupons.setPersent(couponsDTO.getPersent());
			couponsDao.update(coupons);
		}

	}
	
	@Override
	public void delete(int id) {
		Coupons coupons =couponsDao.get(id);
		if (coupons!=null) {
			couponsDao.delete(id);
		}
	}
	
	@Override
	public CouponsDTO get(int id) {
		Coupons coupons =couponsDao.get(id);
		return convert(coupons);
		
	}
	

	@Override
	public List<CouponsDTO> getAll(int start,int lenght) {
		List<Coupons> list =couponsDao.getAll(start, lenght);
		List<CouponsDTO> couponsDTOs =new ArrayList<CouponsDTO>();
		for (Coupons coupons : list) {
			couponsDTOs.add(convert(coupons));
		}
		return couponsDTOs;		
	}
	
	@Override
	public List<CouponsDTO> getListByName(String name,int start,int lenght) {
		List<Coupons> list =couponsDao.getListByName(name, start, lenght);
		List<CouponsDTO> couponsDTOs =new ArrayList<CouponsDTO>();
		for (Coupons coupons : list) {
			couponsDTOs.add(convert(coupons));
		}
		return couponsDTOs;		
	}
	
//	@Override
//	public CouponsDTO getByName(String name,int start,int lenght) {
//		Coupons coupons =couponsDao.getByName(name,start,lenght);
//		CouponsDTO couponsDTO =new CouponsDTO();
//		couponsDTO.setId(coupons.getId());
//		couponsDTO.setName(coupons.getName());
//		couponsDTO.setPersent(coupons.getPersent());
//		return couponsDTO;
//	}
	
	@Override
	public CouponsDTO getByName(String name) {
		Coupons coupons =couponsDao.getByName(name);
		CouponsDTO couponsDTO =new CouponsDTO();
		couponsDTO.setId(coupons.getId());
		couponsDTO.setName(coupons.getName());
		couponsDTO.setPersent(coupons.getPersent());
		return couponsDTO;
	}
	
	@Override
	public long countSearch(String name) {
		long count =couponsDao.countSearch(name);
		return count;
	}
	
	@Override
	public long countAll() {
		long count =couponsDao.countAll();
		return count;
	}
	

	
	private CouponsDTO convert(Coupons coupons) {
		CouponsDTO couponsDTO =new CouponsDTO();
		couponsDTO.setId(coupons.getId());
		couponsDTO.setName(coupons.getName());
		couponsDTO.setPersent(coupons.getPersent());
		
		return couponsDTO;
	}


	

}
