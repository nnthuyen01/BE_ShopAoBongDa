package com.website.aobongda.mapper;

import java.util.List;

import com.website.aobongda.dto.LeagueDTO;
import com.website.aobongda.model.League;

public interface LeagueMapper {
	LeagueDTO toLeagueDTO(League league);

	List<LeagueDTO> toLeagueDTO(List<League> leagues);
}