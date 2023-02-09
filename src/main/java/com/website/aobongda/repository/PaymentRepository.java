package com.website.aobongda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
