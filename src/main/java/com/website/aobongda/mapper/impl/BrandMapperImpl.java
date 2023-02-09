package com.website.aobongda.mapper.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.website.aobongda.dto.BrandDTO;
import com.website.aobongda.mapper.BrandMapper;
import com.website.aobongda.model.Brand;

@Component
public class BrandMapperImpl implements BrandMapper {
	@Override
	public BrandDTO toBrandDTO(Brand brand) {
		BrandDTO brandDTO = new BrandDTO();
		if (brand != null) {
			brandDTO.setId(brand.getId());
			brandDTO.setName(brand.getName());
		}
		return brandDTO;
	}

	@Override
	public List<BrandDTO> toBrandDTO(List<Brand> brands) {
		List<BrandDTO> brandDTOS = new ArrayList<>();
		if (!CollectionUtils.isEmpty(brands)) {
			for (Brand brand : brands) {
				brandDTOS.add(this.toBrandDTO(brand));
			}
		}
		return brandDTOS;
	}

}
