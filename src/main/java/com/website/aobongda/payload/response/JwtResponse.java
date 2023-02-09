package com.website.aobongda.payload.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private Collection<? extends GrantedAuthority> roles;
	
	public JwtResponse(String token, String username, Collection<? extends GrantedAuthority> roles) {
		super();
		this.token = token;
		this.username = username;
		this.roles = roles;
	}
	
	
}
