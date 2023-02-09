package com.website.aobongda.controller;

import javax.validation.Valid;

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

import com.website.aobongda.dto.ClubDTO;
import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.model.Club;
import com.website.aobongda.service.impl.IClubService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
//@SecurityRequirement(name = "AUTHORIZATION")
public class ClubController {
	private final IClubService iClubService;

	// Get all club
	@GetMapping("/clubs")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(iClubService.getAllClubs());
	}

	// Get club by ID
//	@GetMapping("/club/{clubId}")
//	public ResponseEntity<?> findById(@PathVariable Long clubId) {
//		Club club = iClubService.findByID(clubId);
//		if (club != null)
//			return ResponseEntity.ok(new ResponseDTO(true, "Success", club));
//		else
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(false, "Club ID not exits", null));
//	}

	// Add Club
	// requied name Club
	@PostMapping("/post/club")
	public ResponseEntity<?> saveClub(@RequestBody ClubDTO clubDTO) {
		try {
			Club clubSave = iClubService.save(clubDTO);
			return ResponseEntity.ok(new ResponseDTO(true, "Success", clubSave));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDTO(false, e.getMessage(), null));
		}
	}
	
	@PostMapping("/admin/create_club")
	public ResponseEntity<?> createClub(@RequestBody ClubDTO request){
		return ResponseEntity.ok(iClubService.createClub(request));
	}
	
	@GetMapping("/club/{id}")
	public ResponseEntity<?> getClubById(@PathVariable("id") Long id){
		return ResponseEntity.ok(iClubService.getClubById(id));
	}

	// Update club
	@PutMapping("/post/club")
	public ResponseEntity<?> updateClub(@RequestBody ClubDTO clubDTO) {
		Club clubUpdate = iClubService.updateClub(clubDTO);
		if (clubUpdate != null)
			return ResponseEntity.ok(new ResponseDTO(true, "Success", clubUpdate));
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(false, "Club ID not exits", null));
	}

	// Delete club
	@DeleteMapping("/post/club/{id}")
	public ResponseEntity<?> deleteClub(@PathVariable Long id) {
		boolean check = iClubService.deleteClub(id);
		if (check) {
			return ResponseEntity.ok(new ResponseDTO(true, "Success", null));
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(false, "Club ID not exits", null));
	}
	@GetMapping("/club/")
	public ResponseEntity<?> getClubByName(@RequestParam String name){
		return ResponseEntity.ok(iClubService.getClubByName(name));
	}
}
