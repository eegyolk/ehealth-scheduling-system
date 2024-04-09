package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ehealthss.bean.AttendanceDTO;
import com.ehealthss.bean.DoctorAttendanceDTO;
import com.ehealthss.service.AttendanceService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		return attendanceService.index(model, userDetails);

	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/create/{scheduleId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void createAttendance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int scheduleId,
			@RequestBody AttendanceDTO attendanceDTO) {

		attendanceService.create(userDetails, scheduleId, attendanceDTO);

	}

	@ResponseBody
	@PostMapping(value = "/fetch/attendances", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public DataTablesOutput<DoctorAttendanceDTO> fetchAttendances(@AuthenticationPrincipal UserDetails userDetails,
			@Valid @RequestBody DataTablesInput input) {

		return attendanceService.fetchAttendances(userDetails, input);

	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/update/{attendanceId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateAttendance(@PathVariable int attendanceId, @RequestBody AttendanceDTO attendanceDTO) {

		attendanceService.update(attendanceId, attendanceDTO);

	}
}
