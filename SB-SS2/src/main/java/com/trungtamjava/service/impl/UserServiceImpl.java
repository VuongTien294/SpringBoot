package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.trungtamjava.dao.UserDao;
import com.trungtamjava.entity.User;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.model.UserPrincipal;
import com.trungtamjava.service.UserService;
import com.trungtamjava.ultil.PasswordGenerator;


@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
	@Autowired
	UserDao userDao;

	@Override
	public void insert(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setAge(userDTO.getAge());
		user.setUsername(userDTO.getUsername());
		user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
		user.setGender(userDTO.getGender());
		user.setAddress(userDTO.getAddress());
		user.setRole(userDTO.getRole());
		user.setPhone(userDTO.getPhone());
		user.setEmail(userDTO.getEmail());
		user.setEnabled(userDTO.getEnabled());
		userDao.insert(user);
	}

	@Override
	public void update(UserDTO userDTO) {
		User user = userDao.get(userDTO.getId());
		if (user != null) {
			user.setName(userDTO.getName());
			user.setAge(userDTO.getAge());
			user.setUsername(userDTO.getUsername());
			
//			user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
			
			user.setGender(userDTO.getGender());
			user.setAddress(userDTO.getAddress());
			user.setRole(userDTO.getRole());
			user.setPhone(userDTO.getPhone());
			user.setEmail(userDTO.getEmail());
			user.setEnabled(userDTO.getEnabled());
			userDao.update(user);
		}
	}

	@Override
	public void delete(int id) {
		User user = userDao.get(id);
		if (user != null) {
			userDao.delete(id);
		}
	}

	@Override
	public UserDTO get(int id) {
		User user = userDao.get(id);
		return convert(user);
	}

	@Override
	public List<UserDTO> getByName(String name,int start,int length) {
		List<User> list = userDao.getByName(name,start,length);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for (User user : list) {
			userDTOs.add(convert(user));
		}
		return userDTOs;
	}

	@Override
	public List<UserDTO> getAll(int start,int lenght) {
		List<User> users = userDao.getAll(start, lenght);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for (User user : users) {
			userDTOs.add(convert(user));
		}
		return userDTOs;
	}


	private UserDTO convert(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setAge(user.getAge());
		userDTO.setUsername(user.getUsername());
		
//		userDTO.setPassword(user.getPassword());
		
		userDTO.setGender(user.getGender());
		userDTO.setAddress(user.getAddress());
		userDTO.setRole(user.getRole());
		userDTO.setPhone(user.getPhone());
		userDTO.setEmail(user.getEmail());
		userDTO.setEnabled(user.getEnabled());
		return userDTO;
	}
	
	@Override
	public void changePassword(UserDTO userDTO) {
	}
	
	@Override
	public void resetPassword(UserDTO accountDTO) {
	}
	
	@Override
	public void setupUserPassword(UserDTO userDTO) {
		User user = userDao.get(userDTO.getId());
		if (user != null) {
			user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
			userDao.update(user);
		}
	}
	
	@Override
	public void updateProfile(UserDTO userDTO) {
	}
	
	@Override
	public long countSearch(String name) {
		long count=userDao.countSearch(name);
		return count;
	}
	
	@Override
	public long countGetAll() {
		long count =userDao.countGetAll();
		return count;
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		UserPrincipal userPrincipal = new UserPrincipal(user.getUsername(), user.getPassword(), user.getEnabled(), true,
				true, true, authorities);

		userPrincipal.setId(user.getId());
		userPrincipal.setName(user.getName());
		userPrincipal.setRole(user.getRole());
		userPrincipal.setPhone(user.getPhone());
		userPrincipal.setEmail(user.getEmail());
		
		return userPrincipal;
	}

}
