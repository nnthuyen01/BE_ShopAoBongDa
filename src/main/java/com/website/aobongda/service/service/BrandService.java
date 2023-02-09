package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.website.aobongda.dto.BrandDTO;
import com.website.aobongda.exception.NotFoundException;
import com.website.aobongda.model.Brand;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.repository.BrandRepository;
import com.website.aobongda.service.impl.IbrandService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService implements IbrandService{
	@Autowired
	BrandRepository brandRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
    public Brand saveNewBrand(Brand Brand) {
        return brandRepo.save(Brand);
    }

    @Override
    public Brand updateBrand(Long id, Brand Brand) {
        Optional<Brand> optionalBrand = brandRepo.findById(id);
        if (!optionalBrand.isPresent()) {
            throw new NotFoundException("Brand not found");
        }
        if (brandRepo.findByName(Brand.getName()) != null) {
            throw new IllegalArgumentException("Name already exist");
        }
        Brand updateBrand = optionalBrand.get();
        updateBrand.setName(Brand.getName());
        return brandRepo.save(updateBrand);
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepo.deleteById(id);
    }

    @Override
    public List<Brand> getAllBrand(Integer page, Integer size) {
        List<Brand> Brands = brandRepo.findAll();
        PagedListHolder<Brand> pagedListHolder = new PagedListHolder<>(Brands);
        pagedListHolder.setPage(page - 1);
        pagedListHolder.setPageSize(size);
        return pagedListHolder.getPageList();
    }

	@Override
	public DataResponse<BrandDTO> create(BrandDTO request) {
		DataResponse<BrandDTO> response = new DataResponse<>();
		Brand brand = modelMapper.map(request, Brand.class);
		brandRepo.save(brand);
		response.setSuccess(true);
		response.setMessage("Create successful brand");
		response.setData(request);
		return response;
	}

	@Override
	public DataResponse<?> update(Long id, BrandDTO request) {
		DataResponse<?>response = new DataResponse<>();
		Brand brand = brandRepo.getById(id);
		if(brand == null) {
			response.setSuccess(false);
			response.setMessage("Brand not found");
			return response;
		}
		brand.setName(request.getName());
		brandRepo.save(brand);
		response.setSuccess(true);
		response.setMessage("Update successful");
		return response;
	}

	@Override
	public DataResponse<BrandDTO> getAllBrand() {
		DataResponse<BrandDTO> response = new DataResponse<>();
		List<Brand> brands = brandRepo.findAll();
		List<BrandDTO> lisBrand = new ArrayList<>();
		for(Brand brand:brands) {
			lisBrand.add(modelMapper.map(brand, BrandDTO.class));
		}
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setDatas(lisBrand);
		return response;
	}

	@Override
	public DataResponse<BrandDTO> getBrandById(Long id) {
		DataResponse<BrandDTO> response = new DataResponse<>();
		Brand brand = brandRepo.getById(id);
		if(brand == null) {
			response.setSuccess(false);
			response.setMessage("Brand not found");
			return response;
		}
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setData(modelMapper.map(brand, BrandDTO.class));
		return response;
	}
}
