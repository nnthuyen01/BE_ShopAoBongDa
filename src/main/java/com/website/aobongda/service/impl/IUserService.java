package com.website.aobongda.service.impl;

import java.util.Optional;

import com.website.aobongda.model.User;

public interface IUserService {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsByPhone(String phone);

	User findByEmail(String email);

	User save(User user);

	
}
