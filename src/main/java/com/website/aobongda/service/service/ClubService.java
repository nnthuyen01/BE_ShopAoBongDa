package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.website.aobongda.dto.BrandDTO;
import com.website.aobongda.dto.ClubDTO;
import com.website.aobongda.dto.LeagueDTO;
import com.website.aobongda.exception.AppException;
import com.website.aobongda.model.Brand;
import com.website.aobongda.model.Club;
import com.website.aobongda.model.League;
import com.website.aobongda.model.Product;
import com.website.aobongda.payload.response.ClubResponse;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.ClubResponse;
import com.website.aobongda.repository.BrandRepository;
import com.website.aobongda.repository.ClubRepository;
import com.website.aobongda.repository.LeagueRepository;
import com.website.aobongda.repository.ProductRepository;
import com.website.aobongda.service.impl.IClubService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService implements IClubService {
	private final ClubRepository clubRepository;
	private final BrandRepository brandRepository;
	private final LeagueRepository leagueRepository;
	private final ProductRepository repository;
	private final ModelMapper modelMapper;

	@Override
	public Club findByID(Long id) {
		Optional<Club> club = clubRepository.findById(id);
		return club.orElse(null);
	}

	@Override
	public List<Club> findAll() {
		return clubRepository.findAll();
	}

	@Override
	public Club save(ClubDTO clubDTO) {

		boolean check = leagueRepository.existsById(clubDTO.getLeagueId())
				&& brandRepository.existsById(clubDTO.getBrandId());
		if (check) {
			Club club = new Club();
			club = modelMapper.map(clubDTO, Club.class);
			League league = leagueRepository.getReferenceById(clubDTO.getLeagueId());
			club.setLeague(league);
			Brand brand = brandRepository.getReferenceById(clubDTO.getBrandId());
			club.setBrand(brand);
			return clubRepository.save(club);
		} else {
			throw new AppException(404, "Brand or League not exist.");
		}

	}

	@Override
	public Club updateClub(ClubDTO clubDTO) {
		Club clubUpdate = findByID(clubDTO.getId());

		if (clubUpdate != null) {
			clubUpdate.setName(clubDTO.getName());
			return clubRepository.save(clubUpdate);
		} else
			throw new AppException(404, "Club ID not found");
	}

	@Override
	public Boolean deleteClub(Long clubId) {

		Club clubDelete = findByID(clubId);
		if (clubDelete != null) {
			clubRepository.deleteById(clubId);
			return true;
		} else
			return false;
	}

	@Override
	public DataResponse<ClubResponse> createClub(ClubDTO newClub) {
		DataResponse<ClubResponse> response = new DataResponse<>();

		Club club = modelMapper.map(newClub, Club.class);
		Brand brand = brandRepository.getReferenceById(newClub.getBrandId());
        League league = leagueRepository.getReferenceById(newClub.getLeagueId());
		club.setBrand(brand);
		club.setLeague(league);
		clubRepository.save(club);
		ClubResponse clubResponse = modelMapper.map(club, ClubResponse.class);
		response.setSuccess(true);
		response.setMessage("Create Success");
		response.setData(clubResponse);
		return response;
	}

	@Override
	public DataResponse<ClubResponse> getAllClubs() {
		DataResponse<ClubResponse> response = new DataResponse<>();
		List<Club> clubs = clubRepository.findAll();
		if(clubs.size() <= 0) {
			response.setSuccess(false);
			response.setMessage("Clubs is empty");
		}else {
			List<ClubResponse> clbResponses = new ArrayList<>();
			for(Club club: clubs) {
				ClubResponse clubResponse = modelMapper.map(club, ClubResponse.class);
				clubResponse.setBrand(modelMapper.map(club.getBrand(), BrandDTO.class));
				clubResponse.setLeague(modelMapper.map(club.getLeague(), LeagueDTO.class));
				clbResponses.add(clubResponse);
			}
			response.setSuccess(true);
			response.setMessage("Ok");
			response.setDatas(clbResponses);
		}
		
		return response;
	}

	@Override
	public DataResponse<ClubResponse> getClubById(Long id) {
		DataResponse<ClubResponse> response = new DataResponse<>();
		Club club = clubRepository.getById(id);
		if(club == null) {
			response.setSuccess(false);
			response.setMessage("Club not found");
			return response;
		}
		BrandDTO brand = modelMapper.map(club.getBrand(), BrandDTO.class);
		LeagueDTO league = modelMapper.map(club.getLeague(), LeagueDTO.class);
		
		ClubResponse clubResponse = modelMapper.map(club, ClubResponse.class);
		clubResponse.setBrand(brand);
		clubResponse.setLeague(league);
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setData(clubResponse);
		return response;
	}

	@Override
	public DataResponse<ClubDTO> edit(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DataResponse<ClubResponse> getClubByName(String name) {
		DataResponse<ClubResponse> response = new DataResponse<>();
		Club club = clubRepository.findByName(name);
		if (club == null) {
			response.setSuccess(false);
			response.setMessage("Club not found");
			return response;
		}
		ClubResponse clubResponse = modelMapper.map(club, ClubResponse.class);
		clubResponse.setBrand(modelMapper.map(club.getBrand(), BrandDTO.class));
		clubResponse.setLeague(modelMapper.map(club.getLeague(), LeagueDTO.class));
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setData(clubResponse);
		return response;
	}

}
