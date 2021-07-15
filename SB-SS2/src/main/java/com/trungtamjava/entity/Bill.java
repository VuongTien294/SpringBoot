package com.trungtamjava.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "bill")
public class Bill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
	
	@ManyToOne
	@JoinColumn(name = "buyer_id")
    public User buyer;
	
	@Column(name = "status")
	public String status;
	
	@Column(name = "buy_date")
    public Date buyDate;
	
	@Column(name = "price_total")
    public double priceTotal;
	
	@Column(name = "discount_percent")
	private Integer discountPercent;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
	@Column(name = "bill_Products")
	public List<BillProduct> billProducts;
	
	@Column(name = "pay")
	public String pay;
	
    @Column(name = "couponsName")
    public String couponsName;
}
