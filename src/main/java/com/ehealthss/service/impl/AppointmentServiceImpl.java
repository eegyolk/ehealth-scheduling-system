package com.ehealthss.service.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ehealthss.bean.AppointmentActivityDTO;
import com.ehealthss.bean.AppointmentDTO;
import com.ehealthss.bean.CalendarEventRequestDTO;
import com.ehealthss.model.Appointment;
import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.model.AppointmentActivityAlert;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.Location;
import com.ehealthss.model.Patient;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.AppointmentStatus;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.model.enums.PatientGender;
import com.ehealthss.model.enums.UserType;
import com.ehealthss.repository.AppointmentActivityAlertRepository;
import com.ehealthss.repository.AppointmentActivityRepository;
import com.ehealthss.repository.AppointmentRepository;
import com.ehealthss.repository.DoctorRepository;
import com.ehealthss.repository.LocationRepository;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.AppointmentService;

import jakarta.persistence.criteria.Join;

@Service
public class AppointmentServiceImpl implements AppointmentService {

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

	@Autowired
	AppointmentActivityAlertRepository appointmentActivityAlertRepository;

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "appointment/appointment";

		model.addAttribute("pageTitle", "Appointment");

		User user = userRepository.findByUsername(userDetails.getUsername());

		if (user.getType() == UserType.PATIENT) {
			AppointmentStatus[] appointmentStatuses = { AppointmentStatus.CANCELLED };
			PatientGender[] patientGenders = PatientGender.class.getEnumConstants();
			List<Appointment> appointments = appointmentRepository.findByPatientIdAndStatusOrderByCreatedOnDesc(
					user.getPatient().getId(), AppointmentStatus.FULFILLED);

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

			model.addAttribute("appointmentStatuses", appointmentStatuses);
			model.addAttribute("patientGenders", patientGenders);
			model.addAttribute("patientProfile", user.getPatient());
			model.addAttribute("patientSettings", user.getPatient().getPatientSetting());
			model.addAttribute("lastDoctorVisited", assignedDoctors);
			model.addAttribute("lastClinicVisited", assignedLocations);

		} else if (user.getType() == UserType.DOCTOR) {
			AppointmentStatus[] appointmentStatuses = { AppointmentStatus.FULFILLED };

			model.addAttribute("appointmentStatuses", appointmentStatuses);
			model.addAttribute("doctorProfile", user.getDoctor());

		} else if (user.getType() == UserType.STAFF) {
			AppointmentStatus[] appointmentStatuses = AppointmentStatus.class.getEnumConstants();

			model.addAttribute("appointmentStatuses", appointmentStatuses);
			model.addAttribute("staffProfile", user.getStaff());
		}

		DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();
		model.addAttribute("doctorDepartments", doctorDepartments);

		List<Location> locations = locationRepository.findAll();
		model.addAttribute("locations", locations);

		return template;

	}

	@Override
	public Set<Doctor> fetchDoctorsByDepartmentAndLocation(DoctorDepartment doctorDepartment, UserDetails userDetails) {

		User user = userRepository.findByUsername(userDetails.getUsername());

		List<Doctor> doctors = doctorRepository.findByDepartment(doctorDepartment);

		Set<Doctor> doctorsByDepartmentAndLocation = new TreeSet<>();

		for (Doctor doctor : doctors) {
			List<DoctorSchedule> doctorSchedules = doctor.getDoctorSchedules();

			for (DoctorSchedule doctorSchedule : doctorSchedules) {
				if (user.getStaff().getLocation().getId() == doctorSchedule.getLocation().getId()) {
					doctorsByDepartmentAndLocation.add(doctor);
				}
			}
		}

		return doctorsByDepartmentAndLocation;
	}

	@Override
	public List<AppointmentDTO> fetchAppointments(UserDetails userDetails,
			CalendarEventRequestDTO calendarEventRequestDTO) {

		User user = userRepository.findByUsername(userDetails.getUsername());

		Specification<Appointment> specification = (Specification<Appointment>) (root, query, builder) -> {

			if (user.getType() == UserType.PATIENT) {

				/**
				 * Joining the Appointment entity with the Patient entity; filtering by patient
				 * ID and appointment datetime range
				 */
				Join<Appointment, Patient> patient = root.join("patient");
				return builder.and(builder.equal(patient.get("id"), user.getPatient().getId()),
						builder.between(root.get("datetime"), calendarEventRequestDTO.getStartDate(),
								calendarEventRequestDTO.getEndDate()));

			} else if (user.getType() == UserType.DOCTOR) {

				/**
				 * Joining the Appointment entity with the Doctor and Location entities;
				 * filtering by doctor ID, appointment status, location ID, and appointment
				 * datetime range
				 */
				Join<Appointment, Doctor> doctor = root.join("doctor");
				Join<Appointment, Location> location = root.join("location");
				return builder.and(builder.equal(doctor.get("id"), user.getDoctor().getId()),
						root.get("status").in(AppointmentStatus.BOOKED, AppointmentStatus.ARRIVED,
								AppointmentStatus.FULFILLED),
						builder.equal(location.get("id"), calendarEventRequestDTO.getId()),
						builder.between(root.get("datetime"), calendarEventRequestDTO.getStartDate(),
								calendarEventRequestDTO.getEndDate()));

			} else { // STAFF

				/**
				 * Joining the Appointment entity with the Location and Doctor entities;
				 * filtering by location ID, doctor ID, and appointment datetime range
				 */
				Join<Appointment, Location> location = root.join("location");
				Join<Appointment, Doctor> doctor = root.join("doctor");
				return builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()),
						builder.equal(doctor.get("id"), calendarEventRequestDTO.getId()),
						builder.between(root.get("datetime"), calendarEventRequestDTO.getStartDate(),
								calendarEventRequestDTO.getEndDate()));

			}

		};

		return appointmentRepository.findAll(specification).stream()
				.map(appointment -> convertToAppointmentDTO(appointment, false)).collect(Collectors.toList());

	}

	@Override
	public AppointmentDTO fetchAppointment(int appointmentId) {

		Appointment appointment = appointmentRepository.getReferenceById(appointmentId);

		return convertToAppointmentDTO(appointment, true);

	}

	@Override
	@Transactional
	public void updateStatus(UserDetails userDetails, int appointmentId,
			AppointmentActivityDTO appointmentActivityDTO) {

		Appointment appointment = appointmentRepository.getReferenceById(appointmentId);
		appointment.setStatus(appointmentActivityDTO.getStatus());
		appointment.setSlot(appointmentActivityDTO.getSlot());
		appointmentRepository.save(appointment);

		User user = userRepository.findByUsername(userDetails.getUsername());

		AppointmentActivity appointmentActivity = new AppointmentActivity();
		appointmentActivity.setAppointment(appointment);
		appointmentActivity.setUser(user);
		appointmentActivity.setNotes(appointmentActivityDTO.getNotes());
		appointmentActivity.setStatus(appointmentActivityDTO.getStatus());
		appointmentActivityRepository.save(appointmentActivity);

		if (AppointmentStatus.BOOKED == appointmentActivityDTO.getStatus()
				|| AppointmentStatus.FULFILLED == appointmentActivityDTO.getStatus()
				|| AppointmentStatus.WAITLIST == appointmentActivityDTO.getStatus()) {

			AppointmentActivityAlert appointmentActivityAlert = new AppointmentActivityAlert(appointmentActivity, user,
					appointment.getPatient().getUser(), false, null, null);
			appointmentActivityAlertRepository.save(appointmentActivityAlert);

		} else if (AppointmentStatus.ARRIVED == appointmentActivityDTO.getStatus()) {

			AppointmentActivityAlert appointmentActivityAlert = new AppointmentActivityAlert(appointmentActivity, user,
					appointment.getPatient().getUser(), false, null, null);
			appointmentActivityAlertRepository.save(appointmentActivityAlert);

			appointmentActivityAlert = new AppointmentActivityAlert(appointmentActivity, user,
					appointment.getDoctor().getUser(), false, null, null);
			appointmentActivityAlertRepository.save(appointmentActivityAlert);

		} else if (AppointmentStatus.CANCELLED == appointmentActivityDTO.getStatus()) {

			if (UserType.STAFF == user.getType()) {

				AppointmentActivityAlert appointmentActivityAlert = new AppointmentActivityAlert(appointmentActivity,
						user, appointment.getPatient().getUser(), false, null, null);
				appointmentActivityAlertRepository.save(appointmentActivityAlert);

			}

		}

	}

	private AppointmentDTO convertToAppointmentDTO(Appointment appointment, boolean isWithActivity) {

		return new AppointmentDTO(appointment.getId(), isWithActivity ? null : appointment.getPatient(),
				isWithActivity ? null : appointment.getDoctor(), isWithActivity ? null : appointment.getLocation(),
				appointment.getReferenceNo(), appointment.getDatetime(), appointment.getDescription(),
				appointment.getReason(), appointment.getStatus(), appointment.isJoinWaitlist(), appointment.getSlot(),
				appointment.getCreatedOn(), appointment.getUpdatedOn(),
				isWithActivity
						? appointment.getAppointmentActivities().stream().map(this::convertToAppointmentActivityDTO)
								.collect(Collectors.toList())
						: null);

	}

	private AppointmentActivityDTO convertToAppointmentActivityDTO(AppointmentActivity appointmentActivity) {

		return new AppointmentActivityDTO(appointmentActivity.getId(), null, appointmentActivity.getUser(),
				appointmentActivity.getNotes(), appointmentActivity.getStatus(), appointmentActivity.getCreatedOn(), 0);

	}

}
