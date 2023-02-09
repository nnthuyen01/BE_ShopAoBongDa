package com.website.aobongda.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.payload.request.LoginRequest;
import com.website.aobongda.service.service.LoginService;
import com.website.aobongda.utility.datatype.RolePrefix;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {
	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser (@Valid @RequestBody LoginRequest request){
		return ResponseEntity.ok(loginService.authenticateUser(request));
	}
	
	@PostMapping("/admin/login")
	public ResponseEntity<?> loginAdmin (@Valid @RequestBody LoginRequest request){
		return ResponseEntity.ok(loginService.authenticateUser(request));
	}
}
