package com.website.aobongda.dto;

import lombok.Data;

@Data
public class ProductReq {
	private Long id;
	private String name;
	private String description;
	private Long price;
	private int status;
	private Long id_club;
	private String image;
	private Long quantity;
}
