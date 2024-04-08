package com.ehealthss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehealthss.model.Location;
import com.ehealthss.model.LocationAvailability;
import com.ehealthss.model.User;
import com.ehealthss.service.ClinicService;
import com.ehealthss.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clinic")
public class ClinicController {

	@Autowired
	private final ClinicService clinicService;
	private final UserService userService;
	
	public ClinicController(ClinicService clinicService, UserService userService) {
		
		this.clinicService = clinicService;
		this.userService = userService;
		
	}
	
	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		return clinicService.index(model, currentUser);

	}
	
	@ResponseBody
	@PostMapping(value = "/fetch/clinics", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public DataTablesOutput<Location> fetchLocations(@Valid @RequestBody DataTablesInput input) {

		return clinicService.fetchLocations(input);

	}
	
	@ResponseBody
	@PostMapping(value = "/fetch/availability/{locationId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public List<LocationAvailability> fetchAvailability(@PathVariable int locationId) {

		return clinicService.fetchAvailability(locationId);

	}
	
}
