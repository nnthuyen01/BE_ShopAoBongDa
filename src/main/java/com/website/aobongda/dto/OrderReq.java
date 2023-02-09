package com.website.aobongda.dto;

import lombok.Data;

@Data
public class OrderReq {

	private Long id;
	private String name;
	private String phone;
	private String address;
	private String note;
	private Long totalPriceOrigin;
	private Long priceOff;
	private Long priceShip;
	private Long totalPrice;
	private int status;
	private String code;
	private Long id_payment;
	private Long id_user;
	private Long id_voucher;
}
