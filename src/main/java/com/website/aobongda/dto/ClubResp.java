package com.website.aobongda.dto;

import com.website.aobongda.model.Brand;
import com.website.aobongda.model.League;

import lombok.Data;

@Data
public class ClubResp {
	private Long id;
	private String name;
	private League league;
	private Brand brand;
}
