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

import com.ehealthss.bean.DoctorDTO;
import com.ehealthss.bean.DoctorScheduleDTO;
import com.ehealthss.service.DoctorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		return doctorService.index(model, userDetails);

	}

	@ResponseBody
	@PostMapping(value = "/fetch/doctors", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public DataTablesOutput<DoctorDTO> fetchAttendances(@Valid @RequestBody DataTablesInput input) {

		return doctorService.findAll(input);

	}

	@ResponseBody
	@PostMapping(value = "/fetch/schedule/{doctorId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public List<DoctorScheduleDTO> fetchSchedules(@PathVariable int doctorId) {

		return doctorService.fetchSchedules(doctorId);

	}
}
