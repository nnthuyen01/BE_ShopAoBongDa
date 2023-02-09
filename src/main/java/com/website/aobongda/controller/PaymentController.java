package com.website.aobongda.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.model.Payment;
import com.website.aobongda.service.impl.IPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {
	private final IPaymentService iPaymentService;

	// Get All payment
	@GetMapping("/payment")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(new ResponseDTO(true, "Success", iPaymentService.findAll()));
	}

	// Get payment by ID
	@GetMapping("/payment/{paymentId}")
	public ResponseEntity<?> findById(@PathVariable Long paymentId) {
		Payment payment = iPaymentService.findById(paymentId);
		if (payment != null)
			return ResponseEntity.ok(new ResponseDTO(true, "Success", payment));
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseDTO(false, "Payment ID not exits", null));
	}

	// Add payment
	@PostMapping("/payment")
	public ResponseEntity<?> savePayment(@RequestBody Payment payment) {
		Payment paymentSave = iPaymentService.save(payment);
		return ResponseEntity.ok(new ResponseDTO(true, "Success", paymentSave));
	}

	// Update Payment
	@PutMapping("/admin/payment")
	public ResponseEntity<?> updatePayment(@RequestBody Payment payment) {
		Payment paymentUpdate = iPaymentService.updatePayment(payment);
		if (paymentUpdate != null)
			return ResponseEntity.ok(new ResponseDTO(true, "Success", paymentUpdate));
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseDTO(false, "Payment ID not exits", null));
	}

	// Delete Payment
	@DeleteMapping("/admin/payment/{id}")
	public ResponseEntity<?> deletePayment(@PathVariable Long id) {
		boolean check = iPaymentService.deletePayment(id);
		if (check) {
			return ResponseEntity.ok(new ResponseDTO(true, "Success", null));
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseDTO(false, "Payment ID not exits", null));
	}
}
