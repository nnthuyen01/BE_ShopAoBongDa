package com.website.aobongda.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.UserDTO;
import com.website.aobongda.payload.request.ChangePasswordRequest;
import com.website.aobongda.payload.request.ResetPasswordRequest;
import com.website.aobongda.payload.request.StringRequest;
import com.website.aobongda.security.userprincipal.UserPrincipal;
import com.website.aobongda.service.impl.ISignupService;


@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class SignupController {
	@Autowired
	ISignupService signupService;
	
	@PostMapping("/api/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO request) throws UnsupportedEncodingException, MessagingException{
		return ResponseEntity.ok(signupService.createUser(request));
	}
	
	@PostMapping("/api/admin/signup")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody UserDTO request){
		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
		return ResponseEntity.ok(signupService.createAdmin(request, role));
	}
	
	@PostMapping("/api/verify")
	public ResponseEntity<?> confirmedEmail(@Valid @RequestBody StringRequest code){
		return ResponseEntity.ok(signupService.enableUser(code.getText()));
	}
	
	@PostMapping("/api/forgot_password")
	public ResponseEntity<?> updateVerifyCode(@Valid @RequestBody StringRequest email) throws UnsupportedEncodingException, MessagingException{
		return ResponseEntity.ok(signupService.updateResetPasswordCode(email.getText()));
	}
	
	
	@PostMapping("/api/reset_password")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest password, HttpServletRequest request){
		return ResponseEntity.ok(signupService.updatePassword(request, password));
	}
	
	
	@PostMapping("/api/user/change_password")
	public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserPrincipal user, @RequestBody @Valid ChangePasswordRequest request){
		return ResponseEntity.ok(signupService.changePassword(user.getUsername(), request));
	}

}
