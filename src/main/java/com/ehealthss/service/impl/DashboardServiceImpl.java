package com.ehealthss.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.bean.AppointmentActivityDTO;
import com.ehealthss.bean.AppointmentDTO;
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
import com.ehealthss.model.enums.UserType;
import com.ehealthss.repository.AppointmentRepository;
import com.ehealthss.repository.DoctorAttendanceRepository;
import com.ehealthss.repository.DoctorRepository;
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

	@Autowired
	DoctorAttendanceRepository doctorAttendanceRepository;

	@Autowired
	DoctorRepository doctorRepository;

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "dashboard/dashboard";

		model.addAttribute("pageTitle", "Dashboard");

		User user = userRepository.findByUsername(userDetails.getUsername());

		// Overview
		List<Appointment> appointments = this.fetchAppointments(user);

		if (user.getType() == UserType.PATIENT) {

			PatientGender[] patientGenders = PatientGender.class.getEnumConstants();
			DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();

			model.addAttribute("patientGenders", patientGenders);
			model.addAttribute("patientProfile", user.getPatient());
			model.addAttribute("patientSettings", user.getPatient().getPatientSetting());
			model.addAttribute("doctorDepartments", doctorDepartments);

			// Overview
			model.addAttribute("upcomingAppointmentList", appointments.stream().map(item -> {

				try {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date currentDate = formatter.parse(LocalDate.now().toString());
					Date recordDate = formatter.parse(formatter.format(item.getDatetime()));
					long diffInMillies = recordDate.getTime() - currentDate.getTime();
					long diffInDays = diffInMillies > 0 ? TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) : 0;

					if (AppointmentStatus.BOOKED == item.getStatus() && diffInDays > 0) {
						return this.convertToAppointmentDTO(item, false);
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}

				return null;

			}).filter(Objects::nonNull).sorted(Comparator.comparing(AppointmentDTO::getDatetime)).toList());
			
			model.addAttribute("awaitingAppointmentList", appointments.stream().map(item -> {

				if (AppointmentStatus.PENDING == item.getStatus() || AppointmentStatus.WAITLIST == item.getStatus()) {
					return this.convertToAppointmentDTO(item, false);
				}
				return null;

			}).filter(Objects::nonNull).sorted(Comparator.comparing(AppointmentDTO::getDatetime)).toList());
			
			List<AppointmentDTO> myAppointment = appointments.size() > 0 ? appointments.stream().map(item -> {

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				boolean isSameDate = LocalDate.now().toString().equals(formatter.format(item.getDatetime()));

				if (AppointmentStatus.ARRIVED == item.getStatus() && isSameDate) {
					return this.convertToAppointmentDTO(item, false);
				}
				return null;

			}).filter(Objects::nonNull).sorted(Comparator.comparing(AppointmentDTO::getSlot)).toList() : List.of();
			model.addAttribute("myAppointment", myAppointment.size() > 0 ? myAppointment.get(0) : null);

		} else if (user.getType() == UserType.DOCTOR) {

			DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();

			model.addAttribute("doctorDepartments", doctorDepartments);
			model.addAttribute("doctorProfile", user.getDoctor());

			// Overview
			List<AppointmentDTO> nextAppointment = appointments.size() > 0 ? appointments.stream().map(item -> {

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				boolean isSameDate = LocalDate.now().toString().equals(formatter.format(item.getDatetime()));

				if (AppointmentStatus.ARRIVED == item.getStatus() && isSameDate) {
					return this.convertToAppointmentDTO(item, false);
				}
				return null;

			}).filter(Objects::nonNull).sorted(Comparator.comparing(AppointmentDTO::getSlot)).toList() : List.of();
			model.addAttribute("nextAppointment", nextAppointment.size() > 0 ? nextAppointment.get(0) : null);
			
			model.addAttribute("todaysAppointmentList", appointments.stream().map(item -> {

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				boolean isSameDate = LocalDate.now().toString().equals(formatter.format(item.getDatetime()));

				if ((AppointmentStatus.BOOKED == item.getStatus() || AppointmentStatus.ARRIVED == item.getStatus())
						&& isSameDate) {
					return this.convertToAppointmentDTO(item, false);
				}
				return null;

			}).filter(Objects::nonNull).sorted(Comparator.comparing(AppointmentDTO::getSlot)).toList());
			
			model.addAttribute("upcomingAppointmentList", appointments.stream().map(item -> {

				try {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date currentDate = formatter.parse(LocalDate.now().toString());
					Date recordDate = formatter.parse(formatter.format(item.getDatetime()));
					long diffInMillies = recordDate.getTime() - currentDate.getTime();
					long diffInDays = diffInMillies > 0 ? TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
							: 0;

					if (AppointmentStatus.BOOKED == item.getStatus() && diffInDays > 0) {
						return this.convertToAppointmentDTO(item, false);
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}

				return null;

			}).filter(Objects::nonNull).sorted(Comparator.comparing(AppointmentDTO::getDatetime)).toList());

			List<DoctorSchedule> doctorschedule = user.getDoctor().getDoctorSchedules().stream().map(item -> {

				if (LocalDate.now().getDayOfWeek().toString().substring(0, 3).toUpperCase()
						.equals(item.getDayOfWeek().toString())) {
					return item;
				}
				return null;

			}).filter(Objects::nonNull).toList();
			model.addAttribute("assignedClinic",
					doctorschedule.size() > 0 ? doctorschedule.get(0).getLocation() : null);
			
			model.addAttribute("todaysAttendance",
					doctorschedule.size() > 0
							? doctorAttendanceRepository.findByDoctorIdAndLocationIdAndDate(user.getDoctor().getId(),
									doctorschedule.get(0).getLocation().getId(), java.sql.Date.valueOf(LocalDate.now()))
							: null);

		} else if (user.getType() == UserType.STAFF) {

			model.addAttribute("staffProfile", user.getStaff());

			// Overview
			model.addAttribute("awaitingAppointmentList", appointments.stream().map(item -> {

				if (AppointmentStatus.PENDING == item.getStatus() || AppointmentStatus.WAITLIST == item.getStatus()) {
					return this.convertToAppointmentDTO(item, false);
				}
				return null;

			}).filter(Objects::nonNull).sorted(Comparator.comparing(AppointmentDTO::getDatetime)).toList());
			
			model.addAttribute("upcomingAppointmentList", appointments.stream().map(item -> {

				if (AppointmentStatus.BOOKED == item.getStatus() || AppointmentStatus.ARRIVED == item.getStatus()) {
					return this.convertToAppointmentDTO(item, false);
				}
				return null;

			}).filter(Objects::nonNull).sorted(Comparator.comparing(AppointmentDTO::getDatetime)).toList());

			model.addAttribute("assignedClinic", user.getStaff().getLocation());

		}

		List<Location> locations = locationRepository.findAll();
		model.addAttribute("locations", locations);

		// Overview
		model.addAttribute("allAppointments", appointments.size());
		
		model.addAttribute("newAppointments", appointments.stream().map(item -> {

			List<AppointmentStatus> newStatuses = List.of(AppointmentStatus.PENDING, AppointmentStatus.WAITLIST);

			if (newStatuses.contains(item.getStatus())) {
				return item;
			}
			return null;

		}).filter(Objects::nonNull).count());
		
		model.addAttribute("fulfilledAppointments", appointments.stream().map(item -> {

			if (AppointmentStatus.FULFILLED == item.getStatus()) {
				return item;
			}
			return null;

		}).filter(Objects::nonNull).count());
		
		model.addAttribute("cancelledAppointments", appointments.stream().map(item -> {

			if (AppointmentStatus.CANCELLED == item.getStatus()) {
				return item;
			}
			return null;

		}).filter(Objects::nonNull).count());
		
		model.addAttribute("todaysAppointments", appointments.stream().map(item -> {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			boolean isSameDate = LocalDate.now().toString().equals(formatter.format(item.getDatetime()));

			if ((AppointmentStatus.BOOKED == item.getStatus() || AppointmentStatus.ARRIVED == item.getStatus())
					&& isSameDate) {
				return item;
			}
			return null;

		}).filter(Objects::nonNull).count());
		
		model.addAttribute("upcomingAppointments", appointments.stream().map(item -> {

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
