package com.trungtamjava.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.trungtamjava.dao.UserDao;
import com.trungtamjava.entity.User;
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insert(User user) {
		entityManager.persist(user);
	}

	@Override
	public void update(User user) {
		entityManager.merge(user);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(get(id));
	}
	
	@Override
	public User get(int id) {
		return entityManager.find(User.class, id);	
	}
	
	@Override
	public List<User> getByName(String name,int start,int length) {
		String jql ="select u from User u where u.name like :name";
		return entityManager.createQuery(jql,User.class).setParameter("name", "%"+name+"%").setFirstResult(start).setMaxResults(length).getResultList();
	}
	
	@Override
	public User getByUserName(String username) {
		String jql ="select u from User u where u.username = :username";
		return entityManager.createQuery(jql,User.class).setParameter("username",username).getSingleResult();
	}
	
	@Override
	public List<User> getAll(int start,int lenght){
		String jql ="select u from User u";
		Query query =entityManager.createQuery(jql, User.class).setFirstResult(start).setMaxResults(lenght);
		return query.getResultList();
		
	}
	
	@Override
	public long countSearch(String name) {
		String jql="SELECT count(u) FROM User u WHERE u.name LIKE :name";
		return entityManager.createQuery(jql, Long.class).setParameter("name", "%" + name + "%").getSingleResult();
	}
	
	@Override
	public long countGetAll() {
		String jql="SELECT count(u) FROM User u";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}

}
