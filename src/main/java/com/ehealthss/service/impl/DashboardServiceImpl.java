package com.ehealthss.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.Location;
import com.ehealthss.model.Patient;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.AppointmentStatus;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.model.enums.PatientGender;
import com.ehealthss.model.enums.UserType;
import com.ehealthss.repository.AppointmentRepository;
import com.ehealthss.repository.LocationRepository;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.DashboardService;

import jakarta.persistence.criteria.Join;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "dashboard/dashboard";

		model.addAttribute("pageTitle", "Dashboard");

		User user = userRepository.findByUsername(userDetails.getUsername());

		if (user.getType() == UserType.PATIENT) {
			PatientGender[] patientGenders = PatientGender.class.getEnumConstants();
			DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();

			model.addAttribute("patientGenders", patientGenders);
			model.addAttribute("patientProfile", user.getPatient());
			model.addAttribute("patientSettings", user.getPatient().getPatientSetting());
			model.addAttribute("doctorDepartments", doctorDepartments);

		} else if (user.getType() == UserType.DOCTOR) {
			DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();

			model.addAttribute("doctorDepartments", doctorDepartments);
			model.addAttribute("doctorProfile", user.getDoctor());

		} else if (user.getType() == UserType.STAFF) {
			model.addAttribute("staffProfile", user.getStaff());

		}

		List<Location> locations = locationRepository.findAll();
		model.addAttribute("locations", locations);

		// Overview
		model.addAttribute("allAppointments", this.fetchAppointments(user).size());
		model.addAttribute("newAppointments", this.fetchAppointments(user).stream().map(item -> {

			List<AppointmentStatus> newStatuses = List.of(AppointmentStatus.PENDING, AppointmentStatus.WAITLIST,
					AppointmentStatus.BOOKED, AppointmentStatus.ARRIVED);

			if (newStatuses.contains(item.getStatus())) {
				return item;
			}
			return null;

		}).filter(Objects::nonNull).count());
		model.addAttribute("fulfilledAppointments", this.fetchAppointments(user).stream().map(item -> {

			if (AppointmentStatus.FULFILLED == item.getStatus()) {
				return item;
			}
			return null;

		}).filter(Objects::nonNull).count());
		model.addAttribute("cancelledAppointments", this.fetchAppointments(user).stream().map(item -> {

			if (AppointmentStatus.CANCELLED == item.getStatus()) {
				return item;
			}
			return null;

		}).filter(Objects::nonNull).count());
		model.addAttribute("todaysAppointments", this.fetchAppointments(user).stream().map(item -> {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			boolean isSameDate = LocalDate.now().toString().equals(formatter.format(item.getDatetime()));

			if ((AppointmentStatus.BOOKED == item.getStatus() || AppointmentStatus.ARRIVED == item.getStatus())
					&& isSameDate) {
				return item;
			}
			return null;

		}).filter(Objects::nonNull).count());
		model.addAttribute("upcomingAppointments", this.fetchAppointments(user).stream().map(item -> {

			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = formatter.parse(LocalDate.now().toString());
				Date recordDate = formatter.parse(formatter.format(item.getDatetime()));
				long diffInMillies = recordDate.getTime() - currentDate.getTime();
				long diffInDays = diffInMillies > 0 ? TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) : 0;

				if (AppointmentStatus.BOOKED == item.getStatus() && diffInDays > 0) {
					return item;
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}

			return null;

		}).filter(Objects::nonNull).count());

		return template;

	}

	private List<Appointment> fetchAppointments(User user) {

		Specification<Appointment> specification = (Specification<Appointment>) (root, query, builder) -> {

			if (UserType.PATIENT == user.getType()) {

				/**
				 * Joining the Appointment entity with the Patient entity filtering by patient
				 * ID
				 */
				Join<Appointment, Patient> patient = root.join("patient");
				return builder.equal(patient.get("id"), user.getPatient().getId());

			} else if (UserType.DOCTOR == user.getType()) {

				/**
				 * Joining the Appointment entity with the Doctor entity filtering by doctor ID
				 */
				Join<Appointment, Doctor> doctor = root.join("doctor");
				return builder.equal(doctor.get("id"), user.getDoctor().getId());

			} else { // STAFF

				/**
				 * Joining the Appointment entity with the Location entity filtering by location
				 * ID
				 */
				Join<Appointment, Location> location = root.join("location");
				return builder.equal(location.get("id"), user.getStaff().getLocation().getId());

			}

		};

		return appointmentRepository.findAll(specification);

	}

}
