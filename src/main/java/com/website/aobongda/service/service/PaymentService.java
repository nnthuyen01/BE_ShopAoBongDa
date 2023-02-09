package com.website.aobongda.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.model.Payment;
import com.website.aobongda.repository.PaymentRepository;
import com.website.aobongda.service.impl.IPaymentService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class PaymentService implements IPaymentService {
	@Autowired
	PaymentRepository paymentRepo;

	@Override
	public Payment findById(Long id) {
		Optional<Payment> payment = paymentRepo.findById(id);
		return payment.orElse(null);
	}

	@Override
	public List<Payment> findAll() {
		return paymentRepo.findAll();
	}

	@Override
	public Payment save(Payment payment) {
		return paymentRepo.save(payment);
	}

	@Override
	public Payment updatePayment(Payment payment) {
		Payment paymentUpdate = findById(payment.getId());
		if (paymentUpdate != null) {
			paymentUpdate.setName(payment.getName());
			return paymentUpdate;
		} else
			return null;
	}

	@Override
	public Boolean deletePayment(Long paymentId) {
		boolean check = paymentRepo.existsById(paymentId);
		if (check) {
			paymentRepo.deleteById(paymentId);
			return true;
		} else {
			return false;
		}
	}
}
