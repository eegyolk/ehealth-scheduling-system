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

import com.ehealthss.bean.DoctorAttendanceDTO;
import com.ehealthss.service.DoctorAttendanceService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/doctor-attendance")
public class DoctorAttendanceController {

	@Autowired
	private DoctorAttendanceService doctorAttendanceService;

	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		return doctorAttendanceService.index(model, userDetails);

	}

	@ResponseBody
	@PostMapping(value = "/fetch/attendances", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public DataTablesOutput<DoctorAttendanceDTO> fetchAttendances(@AuthenticationPrincipal UserDetails userDetails,
			@Valid @RequestBody DataTablesInput input) {

		return doctorAttendanceService.fetchAttendances(userDetails, input);

	}
}
