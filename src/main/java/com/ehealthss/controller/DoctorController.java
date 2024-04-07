package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.User;
import com.ehealthss.service.DoctorService;
import com.ehealthss.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private final DoctorService doctorService;
	private final UserService userService;
	
	public DoctorController(DoctorService doctorService, UserService userService) {
		
		this.doctorService = doctorService;
		this.userService = userService;
		
	}
	
	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		return doctorService.index(model, currentUser);

	}
	
	@ResponseBody
	@PostMapping(value = "/fetch/doctors", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public DataTablesOutput<Doctor> fetchAttendances(@AuthenticationPrincipal UserDetails userDetails,
			@Valid @RequestBody DataTablesInput input) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		return doctorService.findAll(currentUser, input);

	}
	
}
