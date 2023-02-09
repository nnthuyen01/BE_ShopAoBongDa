package com.website.aobongda.payload.response;

import java.util.Date;
import java.util.List;

import com.website.aobongda.dto.ProductReq;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter @Setter
@RequiredArgsConstructor
public class OrderResponse {
	private Long id;
	private String name, note;
	private String phone;
	private String address;
	private List<ProductReq> products;
	private int status;
	private Date date;
	private Long totalPrice, priceShip, priceOff, totalPriceOrigin;
	private String username;
}
