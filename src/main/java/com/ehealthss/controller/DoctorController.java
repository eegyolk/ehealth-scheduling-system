package com.ehealthss.controller;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.service.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

	private final DoctorService doctorService;

	Logger logger = LoggerFactory.getLogger(DoctorController.class);

	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@PostMapping("/fetchByDepartmentAndLocation")
	public Set<Doctor> doctorsByDepartmentAndLocation(
			@RequestParam(name = "doctorDepartment", required = true) DoctorDepartment doctorDepartment,
			@RequestParam(name = "locationId", required = true) int locationId) {
		List<Doctor> doctors = doctorService.findByDepartment(doctorDepartment);

		Set<Doctor> doctorsByDepartmentAndLocation = new TreeSet<>();

		for (Doctor doctor : doctors) {
			List<DoctorSchedule> doctorSchedules = doctor.getDoctorSchedules();

			for (DoctorSchedule doctorSchedule : doctorSchedules) {
				if (locationId == doctorSchedule.getLocation().getId()) {
					doctorsByDepartmentAndLocation.add(doctor);
				}
			}
		}

		return doctorsByDepartmentAndLocation;
	}

	@PostMapping("/fetchSchedulesByDoctorAndLocation")
	public Set<DoctorSchedule> doctorScheduleByDoctorAndLocation(
			@RequestParam(name = "doctorId", required = true) int id,
			@RequestParam(name = "locationId", required = true) int locationId) {
		Doctor doctor = doctorService.getReferenceById(id);

		Set<DoctorSchedule> doctorScheduleByDoctorAndLocation = new TreeSet<>();

		for (DoctorSchedule doctorSchedule : doctor.getDoctorSchedules()) {
			if (locationId == doctorSchedule.getLocation().getId()) {
				doctorScheduleByDoctorAndLocation.add(doctorSchedule);
			}
		}

		return doctorScheduleByDoctorAndLocation;
	}
}
