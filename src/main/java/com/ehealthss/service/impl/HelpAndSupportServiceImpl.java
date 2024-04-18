package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.User;
import com.ehealthss.model.enums.UserType;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.HelpAndSupportService;

@Service
public class HelpAndSupportServiceImpl implements HelpAndSupportService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "help-and-support/help-and-support";

		model.addAttribute("pageTitle", "Help & Support");
		model.addAttribute("withCalendarComponent", false);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", false);
		model.addAttribute("withMapComponent", false);
		
		User user = userRepository.findByUsername(userDetails.getUsername());

		if (user.getType() == UserType.PATIENT) {
			model.addAttribute("patientProfile", user.getPatient());
			
		} else if (user.getType() == UserType.DOCTOR) {
			model.addAttribute("doctorProfile", user.getDoctor());
			
		} else if (user.getType() == UserType.STAFF) {
			model.addAttribute("staffProfile", user.getStaff());
		}

		return template;

	}
	
}
