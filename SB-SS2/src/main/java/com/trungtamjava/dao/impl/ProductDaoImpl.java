package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.trungtamjava.dao.ProductDao;
import com.trungtamjava.entity.Product;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {
@PersistenceContext
private EntityManager entityManager;
 
@Override
public void insert(Product product) {
	 entityManager.persist(product);
}
@Override
public void update(Product product) {
	entityManager.merge(product);
}
@Override
public void delete(int id) {
	entityManager.remove(get(id));
}
@Override
public Product get(int id) {
	String jql ="select p from Product p inner join Category c on c.id=p.category.id where p.id= :pId";
	return entityManager.createQuery(jql,Product.class).setParameter("pId", id).getSingleResult();
}
@Override
public List<Product> getByName(String name,int start,int lenght){
	String jql ="select p from Product p inner join Category c on c.id =p.category.id where p.name like :name ";
	return entityManager.createQuery(jql, Product.class).setParameter("name","%"+ name+"%").setFirstResult(start).setMaxResults(lenght).getResultList();
}


@Override
public List<Product> getAll(int start ,int length) {
	String jql="SELECT p FROM Product p";
	return entityManager.createQuery(jql, Product.class).setFirstResult(start).setMaxResults(length).getResultList();
}


@Override
public long countGetAll() {
	String jql="SELECT count(p) FROM Product p";
	return entityManager.createQuery(jql, Long.class).getSingleResult();
}


@Override
public long countSearch(String name) {
	String jql="SELECT count(p) FROM Product p WHERE p.name LIKE :name";
	return entityManager.createQuery(jql, Long.class).setParameter("name", "%" + name + "%").getSingleResult();
}

@Override
public List<Product> getByCate(int cateId){
	String jql="select p from Product p inner join Category c on c.id=p.category.id where c.id= :cId";
	return entityManager.createQuery(jql, Product.class).setParameter("cId", cateId).getResultList();
}
}
