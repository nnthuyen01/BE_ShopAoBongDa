package com.website.aobongda.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.website.aobongda.dto.UserDTO;
import com.website.aobongda.payload.request.ChangePasswordRequest;
import com.website.aobongda.payload.request.ResetPasswordRequest;
import com.website.aobongda.payload.response.DataResponse;



public interface ISignupService {
DataResponse<UserDTO> createAdmin(UserDTO request, String role);
	
	void sendVerificationEmail(UserDTO user) throws UnsupportedEncodingException, MessagingException;
	
	DataResponse<UserDTO> createUser(UserDTO request) throws UnsupportedEncodingException, MessagingException;
	
	DataResponse<?> enableUser(String verify);
	DataResponse<?> updateResetPasswordCode(String email) throws UnsupportedEncodingException, MessagingException;
	DataResponse<?> updatePassword(HttpServletRequest request, ResetPasswordRequest password);
	
	DataResponse<?> changePassword(String username, ChangePasswordRequest passwordRequest);
}
