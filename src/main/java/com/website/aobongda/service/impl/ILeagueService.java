package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.dto.LeagueDTO;
import com.website.aobongda.model.League;
import com.website.aobongda.payload.response.DataResponse;

public interface ILeagueService {

	League saveNewLeague(League league);

	League updateLeague(final Long id, League league);

	void deleteLeague(final Long id);

	List<League> getAllLeague(Integer page, Integer size);
	
	DataResponse<LeagueDTO> create (LeagueDTO request);
	DataResponse<?> update(Long id, LeagueDTO request);
	DataResponse<LeagueDTO> getAllLeague();
	DataResponse<LeagueDTO> getLeagueById(Long id);
}

