package com.website.aobongda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.dto.VoucherReq;
import com.website.aobongda.service.impl.IVoucherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VoucherController {
	@Autowired
	private final IVoucherService iVoucherService;

	@GetMapping("/vouchers")
	public ResponseEntity<?> getAllProducts() {
		return ResponseEntity.ok(iVoucherService.getAllVouchers());
	}

	@GetMapping("/voucher/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(iVoucherService.getVoucherById(id));
	}

	@PostMapping("/admin/voucher")
	public ResponseEntity<?> save(@RequestBody VoucherReq voucherReq) {

		return ResponseEntity.ok(new ResponseDTO(true, "Success", iVoucherService.save(voucherReq)));
	}

	@PutMapping("/voucher")
	public ResponseEntity<?> update(@RequestBody VoucherReq voucherReq) {
		return ResponseEntity.ok(iVoucherService.update(voucherReq));
	}

	@GetMapping("/voucher")
	public ResponseEntity<?> get(@RequestParam String code) {

		return ResponseEntity.ok(new ResponseDTO(true, "Success", iVoucherService.get(code)));
	}

	@DeleteMapping("/voucher")
	public ResponseEntity<?> delete(@RequestParam String code) {
		iVoucherService.delete(code);
		return ResponseEntity.ok(new ResponseDTO(true, "Success", null));
	}
}
