package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.trungtamjava.dao.CouponsDao;
import com.trungtamjava.entity.Coupons;


@Repository
@Transactional
public class CouponsDaoImpl implements CouponsDao {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
	public void insert(Coupons coupons) {
    	 entityManager.persist(coupons);
    }
    
    @Override
	public void update(Coupons coupons) {
    	entityManager.merge(coupons);
    }
    
    @Override
	public void delete(int id) {
    	entityManager.remove(get(id));
    }
    
    @Override
	public Coupons get(int id) {
    	return entityManager.find(Coupons.class, id);
    }
    
//    @Override
//	public Coupons getByName(String name,int start,int lenght) {
//    	String jql="select c from Coupons c where c.name = :name";    	
//		Query query = entityManager.createQuery(jql ,Coupons.class).setParameter("name", name ).setFirstResult(start).setMaxResults(lenght);
//		return (Coupons) query.getSingleResult();
//    }
    
	@Override
	public List<Coupons> getListByName(String name,int start,int lenght) {
    	String jql="select c from Coupons c where c.name = :name";    	
//    	return (Coupons)entityManager.createQuery(jql, Coupons.class).setParameter("name",name).getSingleResult(); 
		Query query = entityManager.createQuery(jql ,Coupons.class).setParameter("name", name ).setFirstResult(start).setMaxResults(lenght);
		return  query.getResultList();
    }
    
    
    @Override
	public List<Coupons> getAll(int start,int lenght){
    	String jql="select c from Coupons c";
    	return entityManager.createQuery(jql, Coupons.class).setFirstResult(start).setMaxResults(lenght).getResultList();
    }
    
    @Override
	public long countSearch(String name) {
    	String jql="select count(c) from Coupons c where c.name = :name";
    	return entityManager.createQuery(jql, Long.class).setParameter("name", name).getSingleResult();
    }
    
    @Override
	public long countAll() {
    	String jql ="select count(c) from Coupons c";
    	return entityManager.createQuery(jql, Long.class).getSingleResult();
    }

    @Override
	public Coupons getByName(String name){		
    	String jql="select c from Coupons c where c.name = :name";    	
    	return (Coupons)entityManager.createQuery(jql, Coupons.class).setParameter("name",name).getSingleResult(); 
    	
	}
    
}
