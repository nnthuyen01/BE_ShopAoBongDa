package com.website.aobongda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.BrandDTO;
import com.website.aobongda.service.service.BrandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class BrandController {
	@Autowired
	private final BrandService brandService;

	@PostMapping("/admin/create_brand")
	public ResponseEntity<?> create(@RequestBody BrandDTO request){
		return ResponseEntity.ok(brandService.create(request));
	}
	
	@PutMapping("/admin/update_brand/{id}")
	public ResponseEntity<?> update(@RequestBody BrandDTO request, @PathVariable("id") Long id){
		return ResponseEntity.ok(brandService.update(id, request));
	}
	
	@GetMapping("/brands")
	public ResponseEntity<?> getAllBrands(){
		return ResponseEntity.ok(brandService.getAllBrand());
	}
	
	@GetMapping("/brand/{id}")
	public ResponseEntity<?> getBrandById(@PathVariable("id") Long id){
		return ResponseEntity.ok(brandService.getBrandById(id));
	}
	
}
