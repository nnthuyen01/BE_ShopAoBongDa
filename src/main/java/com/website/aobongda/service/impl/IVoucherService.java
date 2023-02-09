package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.dto.VoucherReq;
import com.website.aobongda.model.Voucher;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.ProductReponse;
import com.website.aobongda.payload.response.VoucherResponse;


public interface IVoucherService {
	DataResponse<VoucherResponse> getAllVouchers();
	DataResponse<VoucherResponse> getVoucherById(Long id);
    Voucher save(VoucherReq voucherReq);
    DataResponse<?> update(VoucherReq voucherReq);
    List<Voucher> get(String code);
    void delete(String code);


}
