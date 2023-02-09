package com.website.aobongda.payload.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class AccountResponse {
	private String email;
	private String name;
	private String phone;
	private String username;
}
