package com.ehealthss.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.DoctorAttendance;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.Location;
import com.ehealthss.model.User;
import com.ehealthss.service.AttendanceService;
import com.ehealthss.service.DoctorAttendanceService;
import com.ehealthss.service.DoctorScheduleService;
import com.ehealthss.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.Join;
import jakarta.validation.Valid;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private final DoctorScheduleService doctorScheduleService;
	private final DoctorAttendanceService doctorAttendanceService;
	private final LocationService locationService;

	public AttendanceServiceImpl(DoctorScheduleService doctorScheduleService,
			DoctorAttendanceService doctorAttendanceService, LocationService locationService) {

		this.doctorScheduleService = doctorScheduleService;
		this.doctorAttendanceService = doctorAttendanceService;
		this.locationService = locationService;

	}

	@Override
	public String index(Model model, User user) {

		String template = "attendance/attendance";

		model.addAttribute("pageTitle", "Attendance");
		model.addAttribute("withCalendarComponent", true);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);

		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
		model.addAttribute("dateToday", formatter.format(localDate));

		// Get doctor schedules
		List<DoctorSchedule> doctorSchedules = doctorScheduleService.findByDoctorId(user.getDoctor().getId());

		for (DoctorSchedule doctorSchedule : doctorSchedules) {

			if (doctorSchedule.getDayOfWeek().toString().equals(localDate.getDayOfWeek().toString().substring(0, 3))) {
				ObjectMapper mapper = new ObjectMapper();

				try {

					// Check if doctor has attendance today
					DoctorAttendance doctorAttendance = doctorAttendanceService.findByDoctorIdAndLocationIdAndDate(
							doctorSchedule.getDoctor().getId(), doctorSchedule.getLocation().getId(),
							Date.valueOf(localDate));

					if (doctorAttendance != null) {
						model.addAttribute("hasAttendance", true);
					}

					model.addAttribute("scheduleToday",
							Base64.getEncoder().encodeToString(mapper.writeValueAsString(doctorSchedule).getBytes()));
				} catch (JsonProcessingException jpe) {
					jpe.printStackTrace();
				}

				break;
			}
		}

		List<Location> locations = locationService.findAll();
		model.addAttribute("locations", locations);

		return template;

	}

	@Override
	public void create(User user, int scheduleId, DoctorAttendance doctorAttendance) {

		DoctorSchedule doctorSchedule = doctorScheduleService.getReferenceById(scheduleId);

		doctorAttendance.setDoctor(user.getDoctor());
		doctorAttendance.setLocation(doctorSchedule.getLocation());
		doctorAttendanceService.save(doctorAttendance);

	}

	@Override
	public DataTablesOutput<DoctorAttendance> fetchAttendances(User currentUser, @Valid DataTablesInput input) {

		Specification<DoctorAttendance> specification = (Specification<DoctorAttendance>) (root, query, builder) -> {

			Column searchByFieldName = input.getColumns().get(7);
			Column searchByFieldValue = input.getColumns().get(8);

			int fieldNum = searchByFieldName.getSearch().getValue() == "" ? 0
					: Integer.valueOf(searchByFieldName.getSearch().getValue());
			String fieldValue = searchByFieldValue.getSearch().getValue();

			if (fieldNum == 1 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "doctor_id" in table
				 * "doctor_attendance" and "location_id" in table "location"
				 */
				Join<DoctorAttendance, Doctor> doctor = root.join("doctor");
				Join<DoctorAttendance, Location> location = root.join("location");
				return builder.and(builder.equal(doctor.get("id"), currentUser.getDoctor().getId()),
						builder.equal(location.get("id"), fieldValue));

			} else if (fieldNum == 2 && fieldValue.length() > 0) {
				
				/**
				 * Will produce additional criteria for filtering data with "doctor_id" and
				 * "date" in table "doctor_attendance"
				 */
				Join<DoctorAttendance, Doctor> doctor = root.join("doctor");
				return builder.and(builder.equal(doctor.get("id"), currentUser.getDoctor().getId()),
						builder.equal(root.get("date"), Date.valueOf(LocalDate.parse(fieldValue))));

			} else {

				/**
				 * Will produce additional criteria for filtering data with "doctor_id" in table
				 * "doctor_attendance"
				 */
				Join<DoctorAttendance, Doctor> doctor = root.join("doctor");
				return builder.equal(doctor.get("id"), currentUser.getDoctor().getId());

			}
		};

		return doctorAttendanceService.findAll(input, specification);
	}

	@Override
	public void update(User user, int attendanceId, DoctorAttendance doctorAttendance) {
		
		DoctorAttendance currentDoctorAttendance = doctorAttendanceService.getReferenceById(attendanceId);
		currentDoctorAttendance.setOutTime(doctorAttendance.getOutTime());
		doctorAttendanceService.save(currentDoctorAttendance);
		
	}

}
