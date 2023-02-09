package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.dto.VoucherReq;
import com.website.aobongda.model.Voucher;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.ProductReponse;
import com.website.aobongda.payload.response.VoucherResponse;
import com.website.aobongda.repository.VoucherRepository;
import com.website.aobongda.service.impl.IVoucherService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VoucherService implements IVoucherService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	VoucherRepository repository;

	@Override
	public DataResponse<VoucherResponse> getAllVouchers() {
		DataResponse<VoucherResponse> response = new DataResponse<>();
		List<Voucher> vouchers = repository.findAll();
		List<VoucherResponse> listVoucher = new ArrayList<>();
		for (Voucher voucher : vouchers) {
			VoucherResponse VoucherResponse = modelMapper.map(voucher, VoucherResponse.class);
			listVoucher.add(VoucherResponse);
		}
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setDatas(listVoucher);
		return response;
	}

	@Override
	public DataResponse<VoucherResponse> getVoucherById(Long id) {
		DataResponse<VoucherResponse> response = new DataResponse<>();
		Voucher voucher = repository.getById(id);
		if (voucher == null) {
			response.setSuccess(false);
			response.setMessage("Voucher not found");
			return response;
		}
		VoucherResponse VoucherResponse = modelMapper.map(voucher, VoucherResponse.class);
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setData(VoucherResponse);
		return response;
	}

	@Override
	public Voucher save(VoucherReq voucherReq) {
		List<Voucher> checkList = repository.findVouchersByCode(voucherReq.getCode());
		if (checkList.size() > 0) {
			return null;
		}
		Voucher voucher = new Voucher();
		voucher = modelMapper.map(voucherReq, Voucher.class);
		return repository.save(voucher);
	}

	@Override
	public DataResponse<?> update(VoucherReq voucherReq) {
		DataResponse<?> response = new DataResponse<>();
		Voucher updateVoucher = repository.getReferenceById(voucherReq.getId());
		if (updateVoucher == null) {
			response.setSuccess(false);
			response.setMessage("Voucher not found");
			return response;
		}
		updateVoucher = modelMapper.map(voucherReq, Voucher.class);
		repository.save(updateVoucher);
		response.setSuccess(true);
		response.setMessage("Update successful");
		return response;
	}

	@Override
	public List<Voucher> get(String code) {
		return repository.findVouchersByCode(code);
	}

	@Override
	public void delete(String code) {
		repository.deleteVoucherByCode(code);
	}
}
