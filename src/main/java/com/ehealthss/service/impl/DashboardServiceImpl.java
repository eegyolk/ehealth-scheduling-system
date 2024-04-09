package com.ehealthss.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "dashboard/dashboard";

		model.addAttribute("pageTitle", "Dashboard");
		model.addAttribute("withCalendarComponent", true);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);
		model.addAttribute("withMapComponent", true);

		return template;

	}
	
}
