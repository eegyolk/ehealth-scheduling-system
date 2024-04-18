package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehealthss.bean.UserDTO;
import com.ehealthss.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping(value = "/update/password", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateStatus(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserDTO userDTO) throws Exception {

		userService.updatePassword(userDetails, userDTO);

	}

}
