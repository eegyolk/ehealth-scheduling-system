package com.ehealthss.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.Location;
import com.ehealthss.model.Patient;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.AppointmentStatus;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.model.enums.PatientGender;
import com.ehealthss.repository.AppointmentActivityRepository;
import com.ehealthss.repository.AppointmentRepository;
import com.ehealthss.repository.DoctorRepository;
import com.ehealthss.repository.LocationRepository;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.BookAppointmentService;

import jakarta.persistence.criteria.Join;
import jakarta.validation.Valid;

@Service
public class BookAppointmentServiceImpl implements BookAppointmentService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LocationRepository locationRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Autowired
	AppointmentActivityRepository appointmentActivityRepository;

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "book-appointment/book-appointment";

		model.addAttribute("pageTitle", "Book Appointment");
		model.addAttribute("withCalendarComponent", true);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);
		
		User user = userRepository.findByUsername(userDetails.getUsername());
		model.addAttribute("patientProfile", user.getPatient());
		
		PatientGender[] patientGenders = PatientGender.class.getEnumConstants();
		model.addAttribute("patientGenders", patientGenders);

		List<Location> locations = locationRepository.findAll();
		model.addAttribute("locations", locations);

		DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();
		model.addAttribute("doctorDepartments", doctorDepartments);

		// Get appointments by patient_id and status, order by created_on descending
		List<Appointment> appointments = appointmentRepository
				.findByPatientIdAndStatusOrderByCreatedOnDesc(user.getPatient().getId(), AppointmentStatus.FULFILLED);

		Set<Doctor> assignedDoctors = new TreeSet<>();
		Set<Location> assignedLocations = new TreeSet<>();

		for (Appointment appointment : appointments) {
			Doctor doctor = appointment.getDoctor();
			doctor.setCreatedOn(appointment.getCreatedOn());
			assignedDoctors.add(doctor);

			Location location = appointment.getLocation();
			location.setCreatedOn(appointment.getCreatedOn());
			assignedLocations.add(location);
		}

		model.addAttribute("lastDoctorVisited", assignedDoctors);
		model.addAttribute("lastClinicVisited", assignedLocations);

		return template;

	}

	@Override
	public Set<Doctor> fetchDoctorsByDepartmentAndLocation(DoctorDepartment doctorDepartment, int locationId) {

		List<Doctor> doctors = doctorRepository.findByDepartment(doctorDepartment);

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

	@Override
	public Set<DoctorSchedule> fetchDoctorSchedulesByDoctorAndLocation(int doctorId, int locationId) {

		Doctor doctor = doctorRepository.getReferenceById(doctorId);

		Set<DoctorSchedule> doctorScheduleByDoctorAndLocation = new TreeSet<>();

		for (DoctorSchedule doctorSchedule : doctor.getDoctorSchedules()) {
			if (locationId == doctorSchedule.getLocation().getId()) {
				doctorScheduleByDoctorAndLocation.add(doctorSchedule);
			}
		}

		return doctorScheduleByDoctorAndLocation;

	}

	@Override
	@Transactional
	public void create(UserDetails userDetails, int doctorId, int locationId, Appointment appointment) {

		Doctor doctor = doctorRepository.getReferenceById(doctorId);
		Location location = locationRepository.getReferenceById(locationId);
		User user = userRepository.findByUsername(userDetails.getUsername());

		appointment.setPatient(user.getPatient());
		appointment.setDoctor(doctor);
		appointment.setLocation(location);
		appointment.setReferenceNo(generateReferenceNo(user.getPatient().getId(), doctorId, locationId));
		appointment.setStatus(AppointmentStatus.PENDING);
		Appointment newAppointment = appointmentRepository.save(appointment);

		AppointmentActivity appointmentActivity = new AppointmentActivity(newAppointment, user,
				"Newly created appointment.", AppointmentStatus.PENDING);
		appointmentActivityRepository.save(appointmentActivity);

	}

	@Override
	public DataTablesOutput<Appointment> fetchAppointments(UserDetails userDetails,
			@Valid @RequestBody DataTablesInput input) {

		User user = userRepository.findByUsername(userDetails.getUsername());

		Specification<Appointment> specification = (Specification<Appointment>) (root, query, builder) -> {
			/**
			 * Will produce additional criteria for filtering data with "patient_id" in
			 * table "appointment"
			 */
			Join<Appointment, Patient> patient = root.join("patient");
			return builder.equal(patient.get("id"), user.getPatient().getId());
		};

		return appointmentRepository.findAll(input, specification);

	}

	@Override
	@Transactional
	public void cancel(UserDetails userDetails, int appointmentId, AppointmentActivity appointmentActivity) {

		Appointment appointment = appointmentRepository.getReferenceById(appointmentId);
		appointment.setStatus(AppointmentStatus.CANCELLED);
		appointment.setUpdatedOn(new Date());
		appointmentRepository.save(appointment);

		User user = userRepository.findByUsername(userDetails.getUsername());

		appointmentActivity.setAppointment(appointment);
		appointmentActivity.setUser(user);
		appointmentActivity.setStatus(AppointmentStatus.CANCELLED);
		appointmentActivityRepository.save(appointmentActivity);

	}

	private String generateReferenceNo(int patientId, int doctorId, int locationId) {
		Random rand = new Random();
		int low = 10;
		int high = 100;
		int randNum = rand.nextInt(high - low) + low;
		LocalDate dt = LocalDate.now();
		return String.join("", "REF", String.valueOf(dt.getYear()).substring(2), String.valueOf(dt.getMonthValue()),
				String.valueOf(dt.getDayOfMonth()), String.valueOf(dt.getDayOfYear()), String.valueOf(patientId),
				String.valueOf(doctorId), String.valueOf(locationId), String.valueOf(randNum));
	}
}
