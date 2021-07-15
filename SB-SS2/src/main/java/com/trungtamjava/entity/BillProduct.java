package com.trungtamjava.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "billproduct")
public class BillProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	@Column(name = "unit_price")
	public double unitPrice;

	@Column(name = "quantity")
	public int quantity;

	@ManyToOne
	@JoinColumn(name = "product_id")
	public Product product;

	@ManyToOne
	@JoinColumn(name = "bill_id")
	public Bill bill;
}
