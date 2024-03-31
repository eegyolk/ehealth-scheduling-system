package com.ehealthss.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.model.User;
import com.ehealthss.service.UserService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	private final UserService userService;

	public DashboardController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("")
	public String index(Model uiModel, @AuthenticationPrincipal UserDetails userDetails) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		uiModel.addAttribute("pageTitle", "Dashboard");
		uiModel.addAttribute("withCalendarComponent", false);
		
		return "dashboard/dashboard";
	}

}
