package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.User;
import com.ehealthss.model.enums.PatientGender;
import com.ehealthss.model.enums.UserType;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "dashboard/dashboard";

		model.addAttribute("pageTitle", "Dashboard");
		model.addAttribute("withCalendarComponent", true);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);
		model.addAttribute("withMapComponent", true);

		User user = userRepository.findByUsername(userDetails.getUsername());

		if (user.getType() == UserType.PATIENT) {
			PatientGender[] patientGenders = PatientGender.class.getEnumConstants();
			model.addAttribute("patientGenders", patientGenders);
			
			model.addAttribute("patientProfile", user.getPatient());
			
		} else if (user.getType() == UserType.DOCTOR) {
			model.addAttribute("doctorProfile", user.getDoctor());
			
		} else if (user.getType() == UserType.STAFF) {
			model.addAttribute("staffProfile", user.getStaff());
		}
		
		return template;

	}
	
}
