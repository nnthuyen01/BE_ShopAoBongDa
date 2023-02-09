package com.website.aobongda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.LeagueDTO;
import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.mapper.LeagueMapper;
import com.website.aobongda.model.League;
import com.website.aobongda.service.service.LeagueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class LeagueController {
	@Autowired
	private final LeagueService leagueService;
	@Autowired
	private final LeagueMapper leagueMapper; 

	@PostMapping("/admin/create_league")
	public ResponseEntity<?> create(@RequestBody LeagueDTO request){
		return ResponseEntity.ok(leagueService.create(request));
	}
	
	@GetMapping("/leagues")
	public ResponseEntity<?> getAllLeagues(){
		return ResponseEntity.ok(leagueService.getAllLeague());
	}
	
	@GetMapping("/league/{id}")
	public ResponseEntity<?> getLeagueById(@PathVariable("id") Long id){
		return ResponseEntity.ok(leagueService.getLeagueById(id));
	}
	
	@PutMapping("/admin/update_league/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody LeagueDTO request){
		return ResponseEntity.ok(leagueService.update(id, request));
	}
	
	
	
	
}
