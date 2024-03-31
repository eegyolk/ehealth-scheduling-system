package com.ehealthss.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.Location;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.service.BookAppointmentService;
import com.ehealthss.service.LocationService;
import com.ehealthss.service.UserService;

@Controller
@RequestMapping("/book-appointment")
public class BookAppointmentController {

	private final BookAppointmentService bookAppointmentService;
	private final UserService userService;
	private final LocationService locationService;

	Logger logger = LoggerFactory.getLogger(BookAppointmentController.class);

	public BookAppointmentController(BookAppointmentService bookAppointmentService, UserService userService,
			LocationService locationService) {
		this.bookAppointmentService = bookAppointmentService;
		this.userService = userService;
		this.locationService = locationService;
	}

	@GetMapping("")
	public String index(Model uiModel, @AuthenticationPrincipal UserDetails userDetails) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		return bookAppointmentService.index(currentUser);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/create/{doctorId}/{locationId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void create(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "doctorId") int doctorId,
			@PathVariable(name = "locationId") int locationId, @RequestBody Appointment appointment) {

		User currentUser = userService.findByLogin(userDetails.getUsername());

		bookAppointmentService.create(currentUser, doctorId, locationId, appointment);
	}
}
