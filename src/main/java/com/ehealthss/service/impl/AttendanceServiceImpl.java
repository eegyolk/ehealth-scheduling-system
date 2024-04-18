package com.ehealthss.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.bean.DoctorAttendanceDTO;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.DoctorAttendance;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.Location;
import com.ehealthss.model.User;
import com.ehealthss.repository.DoctorAttendanceRepository;
import com.ehealthss.repository.DoctorScheduleRepository;
import com.ehealthss.repository.LocationRepository;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.AttendanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.Join;
import jakarta.validation.Valid;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DoctorScheduleRepository doctorScheduleRepository;

	@Autowired
	DoctorAttendanceRepository doctorAttendanceRepository;

	@Autowired
	LocationRepository locationRepository;

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "attendance/attendance";

		model.addAttribute("pageTitle", "Attendance");
		model.addAttribute("withCalendarComponent", true);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);
		
		User user = userRepository.findByUsername(userDetails.getUsername());
		model.addAttribute("doctorProfile", user.getDoctor());

		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
		model.addAttribute("dateToday", formatter.format(localDate));

		// Get doctor schedules
		List<DoctorSchedule> doctorSchedules = doctorScheduleRepository.findByDoctorId(user.getDoctor().getId());

		for (DoctorSchedule doctorSchedule : doctorSchedules) {

			if (doctorSchedule.getDayOfWeek().toString().equals(localDate.getDayOfWeek().toString().substring(0, 3))) {
				ObjectMapper mapper = new ObjectMapper();

				try {

					// Check if doctor has attendance today
					DoctorAttendance doctorAttendance = doctorAttendanceRepository.findByDoctorIdAndLocationIdAndDate(
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

		List<Location> locations = locationRepository.findAll();
		model.addAttribute("locations", locations);

		return template;

	}

	@Override
	public void create(UserDetails userDetails, int scheduleId, DoctorAttendance doctorAttendance) {

		DoctorSchedule doctorSchedule = doctorScheduleRepository.getReferenceById(scheduleId);
		User user = userRepository.findByUsername(userDetails.getUsername());

		doctorAttendance.setDoctor(user.getDoctor());
		doctorAttendance.setLocation(doctorSchedule.getLocation());
		doctorAttendanceRepository.save(doctorAttendance);

	}

	@Override
	public DataTablesOutput<DoctorAttendanceDTO> fetchAttendances(UserDetails userDetails,
			@Valid DataTablesInput input) {

		User user = userRepository.findByUsername(userDetails.getUsername());

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
				return builder.and(builder.equal(doctor.get("id"), user.getDoctor().getId()),
						builder.equal(location.get("id"), fieldValue));

			} else if (fieldNum == 2 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "doctor_id" and
				 * "date" in table "doctor_attendance"
				 */
				Join<DoctorAttendance, Doctor> doctor = root.join("doctor");
				return builder.and(builder.equal(doctor.get("id"), user.getDoctor().getId()),
						builder.equal(root.get("date"), Date.valueOf(LocalDate.parse(fieldValue))));

			} else {

				/**
				 * Will produce additional criteria for filtering data with "doctor_id" in table
				 * "doctor_attendance"
				 */
				Join<DoctorAttendance, Doctor> doctor = root.join("doctor");
				return builder.equal(doctor.get("id"), user.getDoctor().getId());

			}
		};

		return recreateDataTablesOutputDoctorAttendance(doctorAttendanceRepository.findAll(input, specification));

	}

	@Override
	public void update(int attendanceId, DoctorAttendance doctorAttendance) {

		DoctorAttendance existingDoctorAttendance = doctorAttendanceRepository.getReferenceById(attendanceId);

		existingDoctorAttendance.setOutTime(doctorAttendance.getOutTime());
		doctorAttendanceRepository.save(existingDoctorAttendance);

	}

	private DataTablesOutput<DoctorAttendanceDTO> recreateDataTablesOutputDoctorAttendance(
			DataTablesOutput<DoctorAttendance> doctorAttendances) {

		DataTablesOutput<DoctorAttendanceDTO> dataTablesOutputDoctorAttendanceDTO = new DataTablesOutput<>();

		dataTablesOutputDoctorAttendanceDTO.setDraw(doctorAttendances.getDraw());
		dataTablesOutputDoctorAttendanceDTO.setError(doctorAttendances.getError());
		dataTablesOutputDoctorAttendanceDTO.setRecordsFiltered(doctorAttendances.getRecordsFiltered());
		dataTablesOutputDoctorAttendanceDTO.setRecordsTotal(doctorAttendances.getRecordsTotal());
		dataTablesOutputDoctorAttendanceDTO.setSearchPanes(doctorAttendances.getSearchPanes());
		dataTablesOutputDoctorAttendanceDTO.setData(doctorAttendances.getData().stream()
				.map(this::convertToDoctorAttendanceDTO).collect(Collectors.toList()));
		return dataTablesOutputDoctorAttendanceDTO;

	}

	private DoctorAttendanceDTO convertToDoctorAttendanceDTO(DoctorAttendance doctorAttendance) {

		return new DoctorAttendanceDTO(doctorAttendance.getId(), null, doctorAttendance.getLocation(),
				doctorAttendance.getDate(), doctorAttendance.getInTime(), doctorAttendance.getOutTime(), null, null,
				null);

	}
}
