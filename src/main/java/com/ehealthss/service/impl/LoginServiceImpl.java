package com.ehealthss.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Override
	public String index(Model model, boolean hasError, boolean isLogout) {

		String template = "login/login";

		model.addAttribute("pageTitle", "Login");
		model.addAttribute("withCalendarComponent", true);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);
		model.addAttribute("withMapComponent", true);
		model.addAttribute("hasError", hasError);
		model.addAttribute("isLogout", isLogout);

		return template;

	}
	
}
