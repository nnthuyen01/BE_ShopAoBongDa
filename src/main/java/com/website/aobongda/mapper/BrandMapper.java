package com.website.aobongda.mapper;

import java.util.List;

import com.website.aobongda.dto.BrandDTO;
import com.website.aobongda.model.Brand;

public interface BrandMapper {
	BrandDTO toBrandDTO(Brand brand);

	List<BrandDTO> toBrandDTO(List<Brand> brands);
}
