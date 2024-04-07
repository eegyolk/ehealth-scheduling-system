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
import com.ehealthss.model.DoctorAttendance;
import com.ehealthss.model.User;
import com.ehealthss.service.AttendanceService;
import com.ehealthss.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	private final AttendanceService attendanceService;
	private final UserService userService;

	public AttendanceController(AttendanceService attendanceService, UserService userService) {

		this.attendanceService = attendanceService;
		this.userService = userService;

	}

	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		return attendanceService.index(model, currentUser);

	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/create/{scheduleId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void createAttendance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int scheduleId,
			@RequestBody AttendanceDTO attendanceDTO) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		attendanceService.create(currentUser, scheduleId, attendanceDTO);

	}

	@ResponseBody
	@PostMapping(value = "/fetch/attendances", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public DataTablesOutput<DoctorAttendance> fetchAttendances(@AuthenticationPrincipal UserDetails userDetails,
			@Valid @RequestBody DataTablesInput input) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		return attendanceService.fetchAttendances(currentUser, input);

	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/update/{attendanceId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateAttendance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int attendanceId,
			@RequestBody AttendanceDTO attendanceDTO) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		attendanceService.update(currentUser, attendanceId, attendanceDTO);

	}
}
