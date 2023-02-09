package com.website.aobongda.dto;

import java.util.List;

import com.website.aobongda.model.Club;

import lombok.Data;
@Data
public class ProductDetailResp {
	private Long id;
	private String name;
	private String description;
	private Long price;
	private int status;
	private Club club;
//	private List<ProductImageResp> images;
}
