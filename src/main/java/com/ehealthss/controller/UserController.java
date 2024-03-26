package com.ehealthss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.model.User;
import com.ehealthss.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/allUsers")
	public String listAllUsers(Model theModel) {
		List<User> allUsers = userService.findAllUsers();

		theModel.addAttribute("allUsers", allUsers);
		

		return "list-all-users";
	}

}
