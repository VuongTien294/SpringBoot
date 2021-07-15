package com.trungtamjava.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
   public int id;
	
	@Column(name = "name")
   public String name;
	
	@Column(name = "age")
	private String age;
	
	@Column(name = "username")
   public String username;
	
	@Column(name = "password")
   public String password;
	
	@Column(name = "gender")
	private String gender;

	@Column(name = "address")
	private String address;

	@Column(name = "role")
	private String role;

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
}
