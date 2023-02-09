package com.website.aobongda.service.impl;

import com.website.aobongda.payload.request.LoginRequest;
import com.website.aobongda.payload.response.JwtResponse;

public interface ILoginService {
	JwtResponse authenticateUser(LoginRequest request);
}
