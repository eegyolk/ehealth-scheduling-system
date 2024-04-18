package com.ehealthss.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ehealthss.bean.UserDTO;

@Service
public interface UserService {

	void updatePassword(UserDetails userDetails, UserDTO userDTO) throws Exception;

}
