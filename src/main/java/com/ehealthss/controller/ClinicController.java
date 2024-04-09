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
import com.ehealthss.service.ClinicService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clinic")
public class ClinicController {

	@Autowired
	private ClinicService clinicService;

	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		return clinicService.index(model, userDetails);

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
