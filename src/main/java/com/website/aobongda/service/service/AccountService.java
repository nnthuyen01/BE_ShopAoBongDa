package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.website.aobongda.dto.UserDTO;
import com.website.aobongda.model.User;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.repository.UserRepository;
import com.website.aobongda.service.impl.IAccountService;

@Service
@Component
public class AccountService implements IAccountService{
	@Autowired
	UserRepository repository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public DataResponse<UserDTO> getAllAccountUser() {
		DataResponse<UserDTO> response = new DataResponse<>();
		List<User> users = repository.getAllAccountUser();
		List<UserDTO> listAccount = new ArrayList<>();
		for(User user : users) {
			UserDTO account = modelMapper.map(user, UserDTO.class);
			listAccount.add(account);
		}
		
		response.setMessage("List account User");
		response.setSuccess(true);
		response.setDatas(listAccount);
		return response;
	}

	@Override
	public DataResponse<UserDTO> getAccountUserById(Long id) {
		DataResponse<UserDTO> response = new DataResponse<>();
		User user = repository.getById(id);
		if(user.getRoles().toString().equals("ADMIN")) {
			user = null;
		}
		
		if(user != null) {
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			response.setSuccess(true);
			response.setData(userDTO);
		}else {
			response.setSuccess(false);
			response.setMessage("User not found");
		}
		return response;
	}

	@Override
	public DataResponse<?> editAccountUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DataResponse<?> blockAccountUser(Long id) {
		DataResponse<?> response = new DataResponse<>();
		User user = repository.getById(id);
		if(user!=null && user.getRoles().toString().equals("USER")) {
			if(user.isStatus()) {
				user.setStatus(false);
				repository.save(user);
				response.setMessage("Blocked this user");
			}else {
				user.setStatus(true);
				repository.save(user);
				response.setMessage("Open this user");
			}
			response.setSuccess(true);
			
		}else {
			response.setSuccess(false);
			response.setMessage("User not found");
		}
		
		return response;
	}

}
