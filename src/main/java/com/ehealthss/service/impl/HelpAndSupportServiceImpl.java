package com.ehealthss.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.service.HelpAndSupportService;

@Service
public class HelpAndSupportServiceImpl implements HelpAndSupportService {

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "help-and-support/help-and-support";

		model.addAttribute("pageTitle", "Help & Support");
		model.addAttribute("withCalendarComponent", false);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", false);
		model.addAttribute("withMapComponent", false);

		return template;

	}
	
}
