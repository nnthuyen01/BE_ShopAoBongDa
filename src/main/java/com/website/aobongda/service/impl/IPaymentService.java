package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.model.Payment;

public interface IPaymentService {
    Payment findById(Long id);
    List<Payment> findAll();
    Payment save(Payment payment);
    Payment updatePayment(Payment payment);
    Boolean deletePayment(Long paymentId);
}
