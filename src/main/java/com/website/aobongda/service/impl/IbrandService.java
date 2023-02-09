package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.dto.BrandDTO;
import com.website.aobongda.model.Brand;
import com.website.aobongda.payload.response.DataResponse;

public interface IbrandService {
    Brand saveNewBrand(Brand brand);
    Brand updateBrand(final Long id, Brand brand);

    void deleteBrand(final Long id);
    
    List<Brand> getAllBrand(Integer page, Integer size);
    
    DataResponse<BrandDTO> create (BrandDTO request);
	DataResponse<?> update(Long id, BrandDTO request);
	DataResponse<BrandDTO> getAllBrand();
	DataResponse<BrandDTO> getBrandById(Long id);
}
