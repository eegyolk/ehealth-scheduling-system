package com.ehealthss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Location;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.model.enums.PatientGender;
import com.ehealthss.model.enums.UserType;
import com.ehealthss.repository.LocationRepository;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.HelpAndSupportService;

@Service
public class HelpAndSupportServiceImpl implements HelpAndSupportService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LocationRepository locationRepository;
	
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
			PatientGender[] patientGenders = PatientGender.class.getEnumConstants();
			DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();
			
			model.addAttribute("patientGenders", patientGenders);
			model.addAttribute("patientProfile", user.getPatient());
			model.addAttribute("doctorDepartments", doctorDepartments);
			
		} else if (user.getType() == UserType.DOCTOR) {
			DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();
			
			model.addAttribute("doctorDepartments", doctorDepartments);
			model.addAttribute("doctorProfile", user.getDoctor());
			
		} else if (user.getType() == UserType.STAFF) {
			model.addAttribute("staffProfile", user.getStaff());
		}
		
		List<Location> locations = locationRepository.findAll();
		model.addAttribute("locations", locations);

		return template;

	}
	
}
