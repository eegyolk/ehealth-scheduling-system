package com.ehealthss.service.impl;

import com.ehealthss.model.User;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.AuthUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsServiceImpl implements AuthUserDetailsService{

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);

		if (user == null) {
			
			throw new UsernameNotFoundException("Invalid Credentials");
			
		} else {
			
			return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
					.password(user.getPassword()).roles(user.getType().toString()).build();
			
		}

	}

}
