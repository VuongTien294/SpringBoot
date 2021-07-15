package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.trungtamjava.dao.BillProductDao;
import com.trungtamjava.entity.BillProduct;
@Repository
@Transactional
public class BillProductDaoImpl implements BillProductDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	
   @Override
public void insert(BillProduct billProduct) {
	   entityManager.persist(billProduct);
   }
   
   @Override
public void update(BillProduct billProduct) {
	   entityManager.merge(billProduct);
   }
   
   @Override
public void delete(BillProduct billProduct) {
	   entityManager.remove(billProduct);
   }
   
   @Override
public BillProduct get(int id) {
	   return entityManager.find(BillProduct.class, id);
   }
   
   @Override
public List<BillProduct> searchByName(String name){
	   String sql ="select bp from BillProduct bp join bp.product p join bp.bill b where p.name like :pname";
	   return entityManager.createQuery(sql, BillProduct.class).setParameter("pname","%"+name+"%").getResultList();
   }
   
   @Override
public List<BillProduct> searchByBill(int id){
	   String jql ="select bp from BillProduct bp join bp.product p join bp.bill b where b.id=:billId";
	   return entityManager.createQuery(jql, BillProduct.class).setParameter("billId", id).getResultList();
   }
   
}
