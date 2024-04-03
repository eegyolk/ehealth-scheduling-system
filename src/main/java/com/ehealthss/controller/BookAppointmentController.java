package com.ehealthss.controller;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.service.BookAppointmentService;
import com.ehealthss.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/book-appointment")
public class BookAppointmentController {

	@Autowired
	private final BookAppointmentService bookAppointmentService;
	private final UserService userService;

	Logger logger = LoggerFactory.getLogger(BookAppointmentController.class);

	public BookAppointmentController(BookAppointmentService bookAppointmentService, UserService userService) {

		this.bookAppointmentService = bookAppointmentService;
		this.userService = userService;

	}

	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		return bookAppointmentService.index(model, currentUser);
		
	}
	
	@ResponseBody
	@PostMapping("/fetchDoctorsByDepartmentAndLocation")
	public Set<Doctor> fetchDoctorsByDepartmentAndLocation(
			@RequestParam(required = true) DoctorDepartment doctorDepartment,
			@RequestParam(required = true) int locationId) {
		
		return bookAppointmentService.fetchDoctorsByDepartmentAndLocation(doctorDepartment, locationId);
		
	}
	
	@ResponseBody
	@PostMapping("/fetchDoctorSchedulesByDoctorAndLocation")
	public Set<DoctorSchedule> fetchDoctorSchedulesByDoctorAndLocation(
			@RequestParam(required = true) int doctorId,
			@RequestParam(required = true) int locationId) {
		
		return bookAppointmentService.fetchDoctorSchedulesByDoctorAndLocation(doctorId, locationId);
		
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/create/appointment/{doctorId}/{locationId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void createAppointment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int doctorId,
			@PathVariable int locationId, @RequestBody Appointment appointment) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		bookAppointmentService.create(currentUser, doctorId, locationId, appointment);
		
	}
	
	@ResponseBody
	@PostMapping(value = "/fetch/appointments", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public DataTablesOutput<Appointment> fetchAppointments(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody DataTablesInput input) {

		User currentUser = userService.findByLogin(userDetails.getUsername());
		
		return bookAppointmentService.fetchAppointments(currentUser, input);

	}

}
