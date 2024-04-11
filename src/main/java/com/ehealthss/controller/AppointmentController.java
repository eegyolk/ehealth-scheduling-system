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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehealthss.bean.AppointmentDTO;
import com.ehealthss.service.AppointmentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		return appointmentService.index(model, userDetails);

	}
	
	@ResponseBody
	@PostMapping(value = "/fetch/appointments", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public DataTablesOutput<AppointmentDTO> fetchAppointments(@AuthenticationPrincipal UserDetails userDetails,
			@Valid @RequestBody DataTablesInput input) {

		return appointmentService.fetchAppointments(userDetails, input);

	}
	
	@ResponseBody
	@PostMapping(value = "/fetch/appointment/{appointmentId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public AppointmentDTO fetchAppointment(@PathVariable int appointmentId) {

		return appointmentService.fetchAppointment(appointmentId);

	}

}
