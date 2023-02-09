package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.website.aobongda.dto.LeagueDTO;
import com.website.aobongda.exception.NotFoundException;
import com.website.aobongda.model.League;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.repository.LeagueRepository;
import com.website.aobongda.service.impl.ILeagueService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LeagueService implements ILeagueService{
	@Autowired
	LeagueRepository leagueRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
    public League saveNewLeague(League league) {
        return leagueRepo.save(league);
    }

    @Override
    public League updateLeague(Long id, League league) {
        Optional<League> optionalLeague = leagueRepo.findById(id);
        if (!optionalLeague.isPresent()) {
            throw new NotFoundException("League not found");
        }
        if (leagueRepo.findByName(league.getName()) != null) {
            throw new IllegalArgumentException("Name already exist");
        }
        League updateLeague = optionalLeague.get();
        updateLeague.setName(league.getName());
        return leagueRepo.save(updateLeague);
    }	

    @Override
    public void deleteLeague(Long id) {
        leagueRepo.deleteById(id);
    }

    @Override
    public List<League> getAllLeague(Integer page, Integer size) {
        List<League> Leagues = leagueRepo.findAll();
        PagedListHolder<League> pagedListHolder = new PagedListHolder<>(Leagues);
        pagedListHolder.setPage(page - 1);
        pagedListHolder.setPageSize(size);
        return pagedListHolder.getPageList();
    }

	@Override
	public DataResponse<LeagueDTO> create(LeagueDTO request) {
		DataResponse<LeagueDTO> response = new DataResponse<>();
		League league = modelMapper.map(request, League.class);
		leagueRepo.save(league);
		response.setSuccess(true);
		response.setMessage("Create successful league");
		response.setData(request);
		return response;
	}

	@Override
	public DataResponse<?> update(Long id, LeagueDTO request) {
		DataResponse<?>response = new DataResponse<>();
		League league = leagueRepo.getById(id);
		if(league == null) {
			response.setSuccess(false);
			response.setMessage("League not found");
			return response;
		}
		league.setName(request.getName());
		leagueRepo.save(league);
		response.setSuccess(true);
		response.setMessage("Update successful");
		return response;
	}

	@Override
	public DataResponse<LeagueDTO> getAllLeague() {
		DataResponse<LeagueDTO> response = new DataResponse<>();
		List<League> leagues = leagueRepo.findAll();
		List<LeagueDTO> listLeague = new ArrayList<>();
		for(League league:leagues) {
			listLeague.add(modelMapper.map(league, LeagueDTO.class));
		}
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setDatas(listLeague);
		return response;
	}

	@Override
	public DataResponse<LeagueDTO> getLeagueById(Long id) {
		DataResponse<LeagueDTO> response = new DataResponse<>();
		League league = leagueRepo.getById(id);
		if(league == null) {
			response.setSuccess(false);
			response.setMessage("League not found");
			return response;
		}
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setData(modelMapper.map(league, LeagueDTO.class));
		return response;
	}
}