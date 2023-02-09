package com.website.aobongda.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String note;
	
	@Column(nullable = false)
	private Long totalPriceOrigin;
	
	@Column(nullable = false)
	private Long priceOff;
	
	@Column(nullable = false)
	private Long priceShip;
	
	@Column(nullable = false)
	private Long totalPrice;
	
	@Column(nullable = false)
	private int status;
	
	@Column
	private String code;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "date")
	private Date date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", nullable = true)
	private User user;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_payment")
	@JsonManagedReference
	private Payment payment;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_voucher", nullable = true)
	private Voucher voucher;

}
