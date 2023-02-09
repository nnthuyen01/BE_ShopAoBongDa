package com.website.aobongda.Utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.website.aobongda.security.userprincipal.UserPrincipal;

public class Utils {
	public static Long getIdCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Long id = user.getId();
		return Long.valueOf(id);
	}
}
