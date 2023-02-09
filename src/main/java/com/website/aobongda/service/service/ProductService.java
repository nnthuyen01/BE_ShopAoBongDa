package com.website.aobongda.service.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.website.aobongda.dto.ClubDTO;
import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.model.Club;
import com.website.aobongda.model.League;
import com.website.aobongda.model.Product;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.ProductReponse;
import com.website.aobongda.repository.ClubRepository;
import com.website.aobongda.repository.LeagueRepository;
import com.website.aobongda.repository.ProductRepository;
import com.website.aobongda.service.impl.IProductService;

import net.bytebuddy.utility.RandomString;

@Service
public class ProductService implements IProductService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ProductRepository repository;
	@Autowired
	ClubRepository clubRepository;
	@Autowired
	ServletContext application;
	@Autowired
	LeagueRepository leagueRepository;
	
	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

	@Override
	public DataResponse<ProductReq> create(ProductReq request, MultipartFile image) throws IOException {
		DataResponse<ProductReq> response = new DataResponse<>();
		Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        String code = RandomString.make(10);
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(code + image.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(image.getBytes());
        }
		Club club = clubRepository.getById(request.getId_club());
		Product product = modelMapper.map(request, Product.class);
		product.setStatus(1);
		product.setClub(club);
		product.setImage(code + image.getOriginalFilename());
		repository.save(product);
		response.setSuccess(true);
		response.setMessage("Create successful product");
		response.setData(request);
		return response;
	}

	@Override
	public DataResponse<?> update(Long id, ProductReq request, MultipartFile image) throws IOException {
		DataResponse<?> response = new DataResponse<>();
		Product product = repository.getById(id);
		
		if (product == null) {
			response.setSuccess(false);
			response.setMessage("Product not found");
			return response;
		}
		String imageOld = product.getImage();
		int status = product.getStatus();
		product = modelMapper.map(request, Product.class);
		product.setId(id);
		product.setStatus(status);
		product.setImage(imageOld);
		product.setClub(clubRepository.getById(request.getId_club()));
		if(image != null) {
			Path staticPath = Paths.get("static");
	        Path imagePath = Paths.get("images");
	        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
	            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
	        }
	        
	        String code = RandomString.make(10);
	        Path file = CURRENT_FOLDER.resolve(staticPath)
	                .resolve(imagePath).resolve(code + image.getOriginalFilename());
	        
	        Path fileOldPath = CURRENT_FOLDER.resolve(staticPath)
	                .resolve(imagePath).resolve(imageOld);
	        if(fileOldPath!=null) {
	        	Files.delete(fileOldPath);
	        }
	        
	        try (OutputStream os = Files.newOutputStream(file)) {
	            os.write(image.getBytes());
	        }
	        product.setImage(code + image.getOriginalFilename());
		}
		repository.save(product);
		response.setSuccess(true);
		response.setMessage("Update successful");
		return response;
	}

	@Override
	public DataResponse<ProductReponse> getAllProducts() {
		DataResponse<ProductReponse> response = new DataResponse<>();
		List<Product> products = repository.findAll();
		List<ProductReponse> listProduct = new ArrayList<>();
		for (Product product : products) {
			ProductReponse productReponse = modelMapper.map(product, ProductReponse.class);
			productReponse.setClub(modelMapper.map(product.getClub(), ClubDTO.class));
			listProduct.add(productReponse);
		}
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setDatas(listProduct);
		return response;
	}

	@Override
	public DataResponse<ProductReponse> getProductById(Long id) {
		DataResponse<ProductReponse> response = new DataResponse<>();
		Product product = repository.getById(id);
		if (product == null) {
			response.setSuccess(false);
			response.setMessage("Product not found");
			return response;
		}
		ProductReponse productReponse = modelMapper.map(product, ProductReponse.class);
		productReponse.setClub(modelMapper.map(product.getClub(), ClubDTO.class));
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setData(productReponse);
		return response;
	}
	@Override
	public DataResponse<ProductReponse> getProductByName(String name) {
		DataResponse<ProductReponse> response = new DataResponse<>();
		Product product = repository.findByName(name);
		if (product == null) {
			response.setSuccess(false);
			response.setMessage("Product not found");
			return response;
		}
		ProductReponse productReponse = modelMapper.map(product, ProductReponse.class);
		productReponse.setClub(modelMapper.map(product.getClub(), ClubDTO.class));
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setData(productReponse);
		return response;
	}
	
	@Override
	public DataResponse<ProductReponse> getProductByIdClub(Long id){
		DataResponse<ProductReponse> response = new DataResponse<>();
		List<Product> products = repository.getProductByIdClub(id);
		if (products.size() == 0) {
			response.setSuccess(false);
			response.setMessage("Product not found");
			return response;
		}
		
		List<ProductReponse> lisProduct = new ArrayList<>();
		for(Product product : products) {
			ProductReponse productReponse = modelMapper.map(product, ProductReponse.class);
			productReponse.setClub(modelMapper.map(product.getClub(), ClubDTO.class));
			lisProduct.add(productReponse);
		}
		
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setDatas(lisProduct);
		return response;
	}
	
	@Override
	public DataResponse<ProductReponse> search(String keyword){
		DataResponse<ProductReponse> response = new DataResponse<>();
		List<Product> listProducts = repository.findByNameOrClubOrBrand(keyword);
		if(listProducts.size() > 0) {
			List<ProductReponse> list = new ArrayList<>();
			listProducts.forEach(product ->{
				ProductReponse productReponse = modelMapper.map(product, ProductReponse.class);
				productReponse.setClub(modelMapper.map(product.getClub(), ClubDTO.class));
				list.add(productReponse);
			});
			response.setSuccess(true);
			response.setMessage("Ok");
			response.setDatas(list);
		}else {
			response.setSuccess(false);
			response.setMessage("Not found product");
		}
		
		return response;
	}
	
	@Override
	public DataResponse<?> updateStatusProduct(Long id){
		DataResponse<?> response = new DataResponse<>();
		Product product = repository.getById(id);
		if(product.getStatus() == 1) product.setStatus(0);
		else product.setStatus(1);
		repository.save(product);
		response.setSuccess(true);
		response.setMessage("Status update successful");
		return response;
		
	}
	
	@Override
	public DataResponse<ProductReponse> getProductsByLeagueId(Long id) {
		DataResponse<ProductReponse> response = new DataResponse<>();
		League league = leagueRepository.getById(id);
		if(league != null) {
			List<Club> listClubs = clubRepository.findByLeague(league);
			List<ProductReponse> list = new ArrayList<>();
			listClubs.forEach(club -> {
				List<Product> listProducts = repository.getProductByIdClub(club.getId());
				listProducts.forEach(product ->{
					ProductReponse productReponse = modelMapper.map(product, ProductReponse.class);
					productReponse.setClub(modelMapper.map(product.getClub(), ClubDTO.class));
					list.add(productReponse);
				});
			});
			response.setDatas(list);
		}
		return response;
	}
	
	@Override
	public DataResponse<ProductReponse> getProductByTop(){
		DataResponse<ProductReponse> response = new DataResponse<>();
		List<Product> products = repository.getProductByTop();
		List<ProductReponse> listProduct = new ArrayList<>();
		for (Product product : products) {
			ProductReponse productReponse = modelMapper.map(product, ProductReponse.class);
			productReponse.setClub(modelMapper.map(product.getClub(), ClubDTO.class));
			listProduct.add(productReponse);
		}
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setDatas(listProduct);
		return response;
	}
}
