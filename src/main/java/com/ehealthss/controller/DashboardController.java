package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.service.DashboardService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		return dashboardService.index(model, userDetails);

	}

}
