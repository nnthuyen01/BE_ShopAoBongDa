package com.website.aobongda.security.userprincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.website.aobongda.model.User;
import com.website.aobongda.repository.UserRepository;


@Service
public class UserDetailService implements UserDetailsService{

	@Autowired
	UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String usernameWithRolePrefix) throws UsernameNotFoundException {
		String role = usernameWithRolePrefix.substring(0, usernameWithRolePrefix.indexOf('@'));
		String username = usernameWithRolePrefix.substring(usernameWithRolePrefix.indexOf('@') + 1,
				usernameWithRolePrefix.length());
		User user = repository.findByUsernameWithRole(username, role);
		if(user !=null) {
			return UserPrincipal.build(user);
		}
		else {
			throw new UsernameNotFoundException(String.format("%s user not found with username: %s", role, username));
		}
		
	}

}
