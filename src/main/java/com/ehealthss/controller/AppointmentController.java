package com.ehealthss.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ehealthss.bean.AppointmentActivityDTO;
import com.ehealthss.bean.AppointmentDTO;
import com.ehealthss.bean.CalendarEventRequestDTO;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.service.AppointmentService;

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
	@PostMapping("/fetchDoctorsByDepartment")
	public Set<Doctor> fetchDoctorsByDepartment(@AuthenticationPrincipal UserDetails userDetails,
			@RequestParam(required = true) DoctorDepartment doctorDepartment) {

		return appointmentService.fetchDoctorsByDepartmentAndLocation(doctorDepartment, userDetails);

	}

	@ResponseBody
	@PostMapping(value = "/fetch/appointments", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public List<AppointmentDTO> fetchAppointments(@AuthenticationPrincipal UserDetails userDetails,
			@RequestBody CalendarEventRequestDTO calendarEventRequestDTO) {

		return appointmentService.fetchAppointments(userDetails, calendarEventRequestDTO);

	}

	@ResponseBody
	@PostMapping(value = "/fetch/appointment/{appointmentId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public AppointmentDTO fetchAppointment(@PathVariable int appointmentId) {

		return appointmentService.fetchAppointment(appointmentId);

	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/update/status/{appointmentId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateStatus(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int appointmentId,
			@RequestBody AppointmentActivityDTO appointmentActivityDTO) {

		appointmentService.updateStatus(userDetails, appointmentId, appointmentActivityDTO);

	}

}
