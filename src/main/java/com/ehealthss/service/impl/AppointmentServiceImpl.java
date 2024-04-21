package com.ehealthss.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
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
import com.ehealthss.model.Doctor;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.Location;
import com.ehealthss.model.Patient;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.AppointmentStatus;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.model.enums.UserType;
import com.ehealthss.repository.AppointmentActivityRepository;
import com.ehealthss.repository.AppointmentRepository;
import com.ehealthss.repository.DoctorRepository;
import com.ehealthss.repository.LocationRepository;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.AppointmentService;

import jakarta.persistence.criteria.Join;
import jakarta.validation.Valid;

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

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "appointment/appointment";

		model.addAttribute("pageTitle", "Appointment");

		User user = userRepository.findByUsername(userDetails.getUsername());

		if (user.getType() == UserType.DOCTOR) {
			AppointmentStatus[] appointmentStatuses = { AppointmentStatus.BOOKED, AppointmentStatus.ARRIVED,
					AppointmentStatus.FULFILLED };

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

			if (user.getType() == UserType.STAFF) {

				/**
				 * Joins the Appointment entity with Location and Doctor to filter appointments
				 * based on location and doctor name. Returns criteria for appointments matching
				 * staff location ID and doctor's first or last name containing the provided
				 * fieldValue.
				 */
				Join<Appointment, Location> location = root.join("location");
				Join<Appointment, Doctor> doctor = root.join("doctor");
				return builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()),
						builder.equal(doctor.get("id"), calendarEventRequestDTO.getDoctorId()),
						builder.between(root.get("datetime"),
								calendarEventRequestDTO.getStartDate(),
								calendarEventRequestDTO.getEndDate()));

			} else { // DOCTOR

				/**
				 * Joins the Appointment entity with Doctor to filter appointments based on
				 * doctor ID. Returns criteria for appointments matching doctor ID.
				 */
				Join<Appointment, Doctor> doctor = root.join("doctor");
				return builder.and(builder.equal(doctor.get("id"), user.getDoctor().getId()), root.get("status")
						.in(AppointmentStatus.BOOKED, AppointmentStatus.ARRIVED, AppointmentStatus.FULFILLED));

			}

		};

		return appointmentRepository.findAll(specification).stream()
				.map(appointment -> convertToAppointmentDTO(appointment, false)).collect(Collectors.toList());

	}

	@Override
	public DataTablesOutput<AppointmentDTO> fetchAppointments(UserDetails userDetails, @Valid DataTablesInput input) {

		User user = userRepository.findByUsername(userDetails.getUsername());
		System.out.println(userDetails);
		System.out.println(user);

		Specification<Appointment> specification = (Specification<Appointment>) (root, query, builder) -> {

			Column searchByFieldName = input.getColumns().get(11);
			Column searchByFieldValue = input.getColumns().get(12);

			int fieldNum = searchByFieldName.getSearch().getValue() == "" ? 0
					: Integer.valueOf(searchByFieldName.getSearch().getValue());
			String fieldValue = searchByFieldValue.getSearch().getValue();

			if (fieldNum == 1 && fieldValue.length() > 0) { // By Reference No.

				if (user.getType() == UserType.STAFF) {

					/**
					 * Joins the Appointment entity with Location to filter appointments based on
					 * location ID. Returns criteria for appointments matching staff location ID and
					 * appointment reference number containing the provided fieldValue.
					 */
					Join<Appointment, Location> location = root.join("location");
					return builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()),
							builder.like(root.get("referenceNo"), "%" + fieldValue + "%"));

				} else { // DOCTOR

					/**
					 * Joins the Appointment entity with Doctor to filter appointments based on
					 * doctor ID. Returns criteria for appointments matching doctor ID and
					 * appointment reference number containing the provided fieldValue.
					 */
					Join<Appointment, Doctor> doctor = root.join("doctor");
					return builder.and(builder.equal(doctor.get("id"), user.getDoctor().getId()),
							root.get("status").in(AppointmentStatus.BOOKED, AppointmentStatus.ARRIVED,
									AppointmentStatus.FULFILLED),
							builder.like(root.get("referenceNo"), "%" + fieldValue + "%"));
				}

			} else if (fieldNum == 2 && fieldValue.length() > 0) { // By Patient

				if (user.getType() == UserType.STAFF) {

					/**
					 * Joins the Appointment entity with Location and Patient entities to filter
					 * appointments based on location and patient name. Returns criteria for
					 * appointments matching staff location ID and patient's first or last name
					 * containing the provided fieldValue.
					 */
					Join<Appointment, Location> location = root.join("location");
					Join<Appointment, Patient> patient = root.join("patient");
					return builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()),
							builder.or(builder.like(patient.get("firstName"), "%" + fieldValue + "%"),
									builder.like(patient.get("lastName"), "%" + fieldValue + "%")));

				} else { // DOCTOR

					/**
					 * Joins the Appointment entity with Doctor and Patient entities to filter
					 * appointments based on doctor and patient name. Returns criteria for
					 * appointments matching doctor ID and patient's first or last name containing
					 * the provided fieldValue.
					 */
					Join<Appointment, Doctor> doctor = root.join("doctor");
					Join<Appointment, Patient> patient = root.join("patient");
					return builder.and(builder.equal(doctor.get("id"), user.getDoctor().getId()),
							root.get("status").in(AppointmentStatus.BOOKED, AppointmentStatus.ARRIVED,
									AppointmentStatus.FULFILLED),
							builder.or(builder.like(patient.get("firstName"), "%" + fieldValue + "%"),
									builder.like(patient.get("lastName"), "%" + fieldValue + "%")));
				}

			} else if (fieldNum == 3 && fieldValue.length() > 0) { // STAFF only, By Practitioner

				/**
				 * Joins the Appointment entity with Location and Doctor to filter appointments
				 * based on location and doctor name. Returns criteria for appointments matching
				 * staff location ID and doctor's first or last name containing the provided
				 * fieldValue.
				 */
				Join<Appointment, Location> location = root.join("location");
				Join<Appointment, Doctor> doctor = root.join("doctor");
				return builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()),
						builder.or(builder.like(doctor.get("firstName"), "%" + fieldValue + "%"),
								builder.like(doctor.get("lastName"), "%" + fieldValue + "%")));

			} else if (fieldNum == 4 && fieldValue.length() > 0) { // DOCTOR only, By Clinic

				/**
				 * Joins the Appointment entity with Doctor and Location to filter appointments
				 * based on doctor and location IDs. Returns criteria for appointments matching
				 * doctor ID and location ID equal to the provided fieldValue.
				 */
				Join<Appointment, Doctor> doctor = root.join("doctor");
				Join<Appointment, Location> location = root.join("location");
				return builder.and(
						builder.equal(doctor.get("id"), user.getDoctor().getId()), root.get("status")
								.in(AppointmentStatus.BOOKED, AppointmentStatus.ARRIVED, AppointmentStatus.FULFILLED),
						builder.equal(location.get("id"), fieldValue));

			} else if (fieldNum == 5 && fieldValue.length() > 0) { // By Date

				if (user.getType() == UserType.STAFF) {

					/**
					 * Joins the Appointment entity with Location to filter appointments based on
					 * location ID. Returns criteria for appointments matching staff location ID and
					 * appointment date equal to the provided fieldValue.
					 */
					Join<Appointment, Location> location = root.join("location");
					return builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()), builder
							.equal(root.get("datetime").as(Date.class), Date.valueOf(LocalDate.parse(fieldValue))));

				} else { // DOCTOR

					/**
					 * Joins the Appointment entity with Doctor to filter appointments based on
					 * doctor ID. Returns criteria for appointments matching doctor ID and
					 * appointment date equal to the provided fieldValue.
					 */
					Join<Appointment, Doctor> doctor = root.join("doctor");
					return builder.and(builder.equal(doctor.get("id"), user.getDoctor().getId()),
							root.get("status").in(AppointmentStatus.BOOKED, AppointmentStatus.ARRIVED,
									AppointmentStatus.FULFILLED),
							builder.equal(root.get("datetime").as(Date.class),
									Date.valueOf(LocalDate.parse(fieldValue))));

				}

			} else if (fieldNum == 6 && fieldValue.length() > 0) { // By Status

				if (user.getType() == UserType.STAFF) {

					/**
					 * Joins the Appointment entity with Location to filter appointments based on
					 * location ID. Returns criteria for appointments matching location ID and
					 * appointment status equal to the provided fieldValue.
					 */
					Join<Appointment, Location> location = root.join("location");
					return builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()),
							builder.equal(root.get("status"), fieldValue));

				} else { // DOCTOR

					/**
					 * Joins the Appointment entity with Doctor to filter appointments based on
					 * doctor ID. Returns criteria for appointments matching doctor ID and
					 * appointment status equal to the provided fieldValue.
					 */
					Join<Appointment, Doctor> doctor = root.join("doctor");
					return builder.and(builder.equal(doctor.get("id"), user.getDoctor().getId()),
							builder.equal(root.get("status"), fieldValue));

				}

			} else {

				if (user.getType() == UserType.STAFF) {

					/**
					 * Joins the Appointment entity with Location to filter appointments based on
					 * location ID. Returns criteria for appointments matching staff location ID.
					 */
					Join<Appointment, Location> location = root.join("location");
					return builder.equal(location.get("id"), user.getStaff().getLocation().getId());

				} else { // DOCTOR

					/**
					 * Joins the Appointment entity with Doctor to filter appointments based on
					 * doctor ID. Returns criteria for appointments matching doctor ID.
					 */
					Join<Appointment, Doctor> doctor = root.join("doctor");
					return builder.and(builder.equal(doctor.get("id"), user.getDoctor().getId()), root.get("status")
							.in(AppointmentStatus.BOOKED, AppointmentStatus.ARRIVED, AppointmentStatus.FULFILLED));

				}

			}

		};

		return recreateDataTablesOutputDoctorAttendance(appointmentRepository.findAll(input, specification));

	}

	@Override
	public AppointmentDTO fetchAppointment(int appointmentId) {

		Appointment appointment = appointmentRepository.getReferenceById(appointmentId);

		return convertToAppointmentDTO(appointment, true);

	}

	@Override
	@Transactional
	public void updateStatus(UserDetails userDetails, int appointmentId, AppointmentActivity appointmentActivity) {

		Appointment appointment = appointmentRepository.getReferenceById(appointmentId);
		appointment.setStatus(appointmentActivity.getStatus());
		appointmentRepository.save(appointment);

		User user = userRepository.findByUsername(userDetails.getUsername());

		appointmentActivity.setAppointment(appointment);
		appointmentActivity.setUser(user);
		appointmentActivityRepository.save(appointmentActivity);

	}

	private DataTablesOutput<AppointmentDTO> recreateDataTablesOutputDoctorAttendance(
			DataTablesOutput<Appointment> appointments) {

		DataTablesOutput<AppointmentDTO> dataTablesOutputAppointmentDTO = new DataTablesOutput<>();

		dataTablesOutputAppointmentDTO.setDraw(appointments.getDraw());
		dataTablesOutputAppointmentDTO.setError(appointments.getError());
		dataTablesOutputAppointmentDTO.setRecordsFiltered(appointments.getRecordsFiltered());
		dataTablesOutputAppointmentDTO.setRecordsTotal(appointments.getRecordsTotal());
		dataTablesOutputAppointmentDTO.setSearchPanes(appointments.getSearchPanes());
		dataTablesOutputAppointmentDTO.setData(appointments.getData().stream()
				.map(appointment -> convertToAppointmentDTO(appointment, false)).collect(Collectors.toList()));
		return dataTablesOutputAppointmentDTO;

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
				appointmentActivity.getNotes(), appointmentActivity.getStatus(), appointmentActivity.getCreatedOn());

	}

}
