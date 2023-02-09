package com.website.aobongda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.website.aobongda.model.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Long>{
	@Query("SELECT v FROM Voucher as v WHERE v.code = :code ")
	Voucher findByCode(String code);
	
	@Query("SELECT v FROM Voucher as v WHERE v.code = :code ")
	List<Voucher> findVouchersByCode(String code);
	void deleteVoucherByCode(String code);
}