package com.ehealthss.service;

import com.ehealthss.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User byLogin = userService.findByLogin(username);

		if (byLogin == null) {
			throw new UsernameNotFoundException("Invalid Credentials");
		} else {
			return org.springframework.security.core.userdetails.User.builder().username(byLogin.getUsername())
					.password(byLogin.getPassword()).roles(byLogin.getType().toString()).build();
		}

	}

}
