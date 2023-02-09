package com.website.aobongda.service.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.website.aobongda.model.User;
import com.website.aobongda.repository.UserRepository;
import com.website.aobongda.service.impl.IUserService;


@Service
public class UserService implements IUserService{
	
	@Autowired
	UserRepository repository;

	@Override
	public Optional<User> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}

	@Override
	public Boolean existsByPhone(String phone) {
		return repository.existsByPhone(phone);
	}

	@Override
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

}
