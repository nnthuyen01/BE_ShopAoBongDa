package com.website.aobongda.dto;

import lombok.Data;

@Data
public class VoucherReq {
	private Long id;
	private String code;
	private Long price;
	private int status;
}