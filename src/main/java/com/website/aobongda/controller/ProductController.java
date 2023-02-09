package com.website.aobongda.controller;

import java.io.IOException;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.service.impl.IProductService;
import com.website.aobongda.service.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {
	@Autowired
	private final IProductService iproductService;

	@PostMapping(value = "/admin/create_product")
	private ResponseEntity<?> create(@RequestParam String name, @RequestParam String description,
			@RequestParam Long price, @RequestParam Long id_club, @RequestParam MultipartFile img) throws IOException {
		ProductReq productReq = new ProductReq();
		productReq.setName(name);
		productReq.setDescription(description);
		productReq.setPrice(price);
		productReq.setId_club(id_club);
		return ResponseEntity.ok(iproductService.create(productReq, img));
	}
	
	@GetMapping("/products")
	public ResponseEntity<?> getAllProducts(){
		return ResponseEntity.ok(iproductService.getAllProducts());
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
		return ResponseEntity.ok(iproductService.getProductById(id));
	}
	
	@GetMapping("/product/")
	public ResponseEntity<?> getProductByName(@RequestParam String name){
		return ResponseEntity.ok(iproductService.getProductByName(name));
	}
	
	@PutMapping("/admin/update_product/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestParam String name, @RequestParam String description, @RequestParam Long price,
			@RequestParam Long id_club, @RequestParam(name = "img", required = false) MultipartFile img) throws IOException {
		ProductReq productReq = new ProductReq();
		productReq.setName(name);
		productReq.setDescription(description);
		productReq.setPrice(price);
		productReq.setId_club(id_club);
		return ResponseEntity.ok(iproductService.update(id, productReq, img));
	}
	
	@GetMapping("/product")
	public ResponseEntity<?> getProductByIdClub(@RequestParam("id_club") Long id_club){
		return ResponseEntity.ok(iproductService.getProductByIdClub(id_club));
	}
	
	@GetMapping("/product/search")
	public ResponseEntity<?> search(@RequestParam String keyword){
		return ResponseEntity.ok(iproductService.search(keyword));
	}
	
	@GetMapping("/admin/product/change_status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable("id") Long id){
		return ResponseEntity.ok(iproductService.updateStatusProduct(id));
	}
	
	@GetMapping("/product_league")
	public ResponseEntity<?> getProductsByLeagueId(@RequestParam("id_league") Long id_league){
		return ResponseEntity.ok(iproductService.getProductsByLeagueId(id_league));
	}
	
	@GetMapping("/product_top")
	public ResponseEntity<?> getProductByTop(){
		return ResponseEntity.ok(iproductService.getProductByTop());
	}
}
