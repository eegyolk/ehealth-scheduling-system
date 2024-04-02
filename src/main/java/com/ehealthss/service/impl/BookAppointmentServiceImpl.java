package com.ehealthss.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.Location;
import com.ehealthss.model.Patient;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.AppointmentStatus;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.service.AppointmentActivityService;
import com.ehealthss.service.AppointmentService;
import com.ehealthss.service.BookAppointmentService;
import com.ehealthss.service.DoctorService;
import com.ehealthss.service.LocationService;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;

@Service
public class BookAppointmentServiceImpl implements BookAppointmentService {

	@Autowired
	private final DoctorService doctorService;
	private final LocationService locationService;
	private final AppointmentService appointmentService;
	private final AppointmentActivityService appointmentActivityService;

	Logger logger = LoggerFactory.getLogger(BookAppointmentServiceImpl.class);

	public BookAppointmentServiceImpl(DoctorService doctorService, LocationService locationService,
			AppointmentService appointmentService, AppointmentActivityService appointmentActivityService) {

		this.doctorService = doctorService;
		this.locationService = locationService;
		this.appointmentService = appointmentService;
		this.appointmentActivityService = appointmentActivityService;

	}

	@Override
	public String index(Model model, User user) {

		String template = "book-appointment/book-appointment";

		model.addAttribute("pageTitle", "Book Appointment");
		model.addAttribute("withCalendarComponent", true);
		model.addAttribute("withTableComponent", true);

		List<Location> locations = locationService.findAll();
		model.addAttribute("locations", locations);

		DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();
		model.addAttribute("doctorDepartments", doctorDepartments);

		return template;

	}

	@Override
	@Transactional
	public void create(User user, int doctorId, int locationId, Appointment appointment) {

		Doctor doctor = doctorService.getReferenceById(doctorId);
		Location location = locationService.getReferenceById(locationId);

		appointment.setPatient(user.getPatient());
		appointment.setDoctor(doctor);
		appointment.setLocation(location);
		appointment.setReferenceNo(
				appointmentService.generateReferenceNo(user.getPatient().getId(), doctorId, locationId));
		appointment.setStatus(AppointmentStatus.PENDING);
		Appointment newAppointment = appointmentService.save(appointment);

		AppointmentActivity appointmentActivity = new AppointmentActivity(newAppointment, user,
				"Newly created appointment.", AppointmentStatus.PENDING);
		appointmentActivityService.save(appointmentActivity);

	}

	@Override
	public DataTablesOutput<Appointment> fetchAppointments(User currentUser, @Valid @RequestBody DataTablesInput input) {
		
		Specification<Appointment> specification = (Specification<Appointment>) (root, query, builder) -> {
			/**
			 * This will create a criteria to select data for the currentUser only
			 */
			Join<Appointment, Patient> appointmentPatient = root.join("patient");
			Predicate criteria = builder.equal(appointmentPatient.get("id"), currentUser.getPatient().getId());
			return criteria;
			
			//TODO: Add for search by reference no.
		};

		return appointmentService.findAll(input, specification);
		
	}

}
