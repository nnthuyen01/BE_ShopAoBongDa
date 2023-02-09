package com.website.aobongda.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class UserDTO {
	protected Long id;
	protected String name;
	protected String password;
	protected String username;
	protected String phone;
	protected String email;
	protected String verificationCode;
	protected boolean enabled;
	protected boolean status;
}
