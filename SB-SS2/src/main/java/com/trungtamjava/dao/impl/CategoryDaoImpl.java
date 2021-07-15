package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.trungtamjava.dao.CategoryDao;
import com.trungtamjava.entity.Category;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {
   @PersistenceContext
   private EntityManager entityManager;
   
   @Override
public void insert(Category category) {
	   entityManager.persist(category);
   }
   
   @Override
public void update(Category category) {
	   entityManager.merge(category);
   }
   
   @Override
public void delete(int id) {
	   entityManager.remove(get(id));
   }
   
   @Override
public Category get(int id) {
	   return entityManager.find(Category.class, id);
   }
   
   @Override
public List<Category> getByName(String name,int start,int lenght){
	   String jql ="select c from Category c where c.name like :name";
	   return entityManager.createQuery(jql,Category.class).setParameter("name","%"+name+"%").setFirstResult(start).setMaxResults(lenght).getResultList();
	   
   }
   
   @Override
public List<Category> getAll(int start,int lenght){
	   String jql ="select c from Category c ";
	   return entityManager.createQuery(jql,Category.class).setFirstResult(start).setMaxResults(lenght).getResultList();
	   
   }
   
   @Override
public long countSearch(String name) {
	   String jql = "select count(c) from Category c where c.name like :name";
	   return entityManager.createQuery(jql,Long.class).setParameter("name","%"+name+"%").getSingleResult();
   }
   
   @Override
public long countGetAll() {
	   String jql = "select count(c) from Category c";
	   return entityManager.createQuery(jql, Long.class).getSingleResult();
   }
   
}
