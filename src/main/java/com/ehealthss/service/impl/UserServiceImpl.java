package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ehealthss.bean.UserDTO;
import com.ehealthss.model.User;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void updatePassword(UserDetails userDetails, UserDTO userDTO) throws Exception {
		
		User user = userRepository.findByUsername(userDetails.getUsername());
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if (!encoder.matches(userDTO.getOldPassword(), user.getPassword())) {
			throw new Exception("Old password doesn't match.");
		}
	
		user.setPassword(encoder.encode(userDTO.getNewPassword()));
		userRepository.save(user);
		
	}

}
