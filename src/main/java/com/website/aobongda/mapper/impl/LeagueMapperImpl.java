package com.website.aobongda.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.website.aobongda.dto.LeagueDTO;

import com.website.aobongda.mapper.LeagueMapper;
import com.website.aobongda.model.League;

@Component
public class LeagueMapperImpl implements LeagueMapper {
	@Override
	public LeagueDTO toLeagueDTO(League league) {
		LeagueDTO leagueDTO = new LeagueDTO();
		if (league != null) {
			leagueDTO.setId(league.getId());
			leagueDTO.setName(league.getName());
		}
		return leagueDTO;
	}

	@Override
	public List<LeagueDTO> toLeagueDTO(List<League> leagues) {
		List<LeagueDTO> leagueDTOS = new ArrayList<>();
		if (!CollectionUtils.isEmpty(leagues)) {
			for (League league : leagues) {
				leagueDTOS.add(this.toLeagueDTO(league));
			}
		}
		return leagueDTOS;
	}

}
