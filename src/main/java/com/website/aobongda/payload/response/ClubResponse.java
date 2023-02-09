package com.website.aobongda.payload.response;

import com.website.aobongda.dto.BrandDTO;
import com.website.aobongda.dto.LeagueDTO;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class ClubResponse {
	private long id;
	private String name;
	BrandDTO brand;
	LeagueDTO league;
}
