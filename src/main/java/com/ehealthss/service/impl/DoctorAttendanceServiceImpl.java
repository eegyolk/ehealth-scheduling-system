package com.ehealthss.service.impl;

import java.sql.Date;
import java.time.LocalDate;
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
import com.ehealthss.model.Location;
import com.ehealthss.model.User;
import com.ehealthss.repository.DoctorAttendanceRepository;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.DoctorAttendanceService;

import jakarta.persistence.criteria.Join;
import jakarta.validation.Valid;

@Service
public class DoctorAttendanceServiceImpl implements DoctorAttendanceService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DoctorAttendanceRepository doctorAttendanceRepository;

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "doctor-attendance/doctor-attendance";

		model.addAttribute("pageTitle", "Doctor Attendance");
		model.addAttribute("withCalendarComponent", true);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);
		model.addAttribute("withMapComponent", false);

		return template;

	}

	@Override
	public DataTablesOutput<DoctorAttendanceDTO> fetchAttendances(UserDetails userDetails,
			@Valid DataTablesInput input) {

		User user = userRepository.findByUsername(userDetails.getUsername());

		Specification<DoctorAttendance> specification = (Specification<DoctorAttendance>) (root, query, builder) -> {

			Column searchByFieldName = input.getColumns().get(8);
			Column searchByFieldValue = input.getColumns().get(9);

			int fieldNum = searchByFieldName.getSearch().getValue() == "" ? 0
					: Integer.valueOf(searchByFieldName.getSearch().getValue());
			String fieldValue = searchByFieldValue.getSearch().getValue();

			if (fieldNum == 1 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "location_id" in
				 * table "doctor_attendance" and "first_name" or "last_name" in table "doctor" by
				 * joining the two tables
				 */
				Join<DoctorAttendance, Location> location = root.join("location");
				Join<DoctorAttendance, Doctor> doctor = root.join("doctor");
				return builder.or(
						builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()),
								builder.like(doctor.get("firstName"), "%" + fieldValue + "%")),
						builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()),
								builder.like(doctor.get("lastName"), "%" + fieldValue + "%")));

			} else if (fieldNum == 2 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "location_id" and
				 * "date" in table "doctor_attendance"
				 */
				Join<DoctorAttendance, Location> location = root.join("location");
				return builder.and(builder.equal(location.get("id"), user.getStaff().getLocation().getId()),
						builder.equal(root.get("date"), Date.valueOf(LocalDate.parse(fieldValue))));

			} else {

				/**
				 * Will produce additional criteria for filtering data with "location_id" in
				 * table "doctor_attendance"
				 */
				Join<DoctorAttendance, Location> location = root.join("location");
				return builder.equal(location.get("id"), user.getStaff().getLocation().getId());

			}
		};

		return recreateDataTablesOutputDoctorAttendance(doctorAttendanceRepository.findAll(input, specification));

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

		return new DoctorAttendanceDTO(doctorAttendance.getId(), doctorAttendance.getDoctor(),
				doctorAttendance.getLocation(), doctorAttendance.getDate(), doctorAttendance.getInTime(),
				doctorAttendance.getOutTime(), null, doctorAttendance.getCreatedOn(), doctorAttendance.getUpdatedOn());

	}
}
