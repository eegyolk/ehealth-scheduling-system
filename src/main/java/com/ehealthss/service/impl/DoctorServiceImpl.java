package com.ehealthss.service.impl;

import java.sql.Date;
import java.time.LocalDate;
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
import com.ehealthss.bean.DoctorDTO;
import com.ehealthss.bean.DoctorScheduleDTO;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.DoctorAttendance;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.repository.DoctorAttendanceRepository;
import com.ehealthss.repository.DoctorRepository;
import com.ehealthss.repository.DoctorScheduleRepository;
import com.ehealthss.service.DoctorService;

import jakarta.validation.Valid;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	DoctorScheduleRepository doctorScheduleRepository;

	@Autowired
	DoctorAttendanceRepository doctorAttendanceRepository;

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "doctor/doctor";

		model.addAttribute("pageTitle", "Doctor");
		model.addAttribute("withCalendarComponent", false);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);

		DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();
		model.addAttribute("doctorDepartments", doctorDepartments);

		return template;

	}

	@Override
	public DataTablesOutput<DoctorDTO> findAll(@Valid DataTablesInput input) {

		Specification<Doctor> specification = (Specification<Doctor>) (root, query, builder) -> {

			Column searchByFieldName = input.getColumns().get(7);
			Column searchByFieldValue = input.getColumns().get(8);

			int fieldNum = searchByFieldName.getSearch().getValue() == "" ? 0
					: Integer.valueOf(searchByFieldName.getSearch().getValue());
			String fieldValue = searchByFieldValue.getSearch().getValue().trim();

			if (fieldNum == 1 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "first_name" or
				 * "last_name" in table "doctor"
				 */
				return builder.or(builder.like(root.get("firstName"), "%" + fieldValue + "%"),
						builder.like(root.get("lastName"), "%" + fieldValue + "%"));

			} else if (fieldNum == 2 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "department" in
				 * table "doctor"
				 */
				return builder.equal(root.get("department"), fieldValue);

			} else {

				return null;

			}

		};

		return convertToDataTablesOutputDoctorDTO(doctorRepository.findAll(input, specification));

	}

	@Override
	public List<DoctorScheduleDTO> fetchSchedules(int doctorId) {

		List<DoctorSchedule> doctorSchedules = doctorScheduleRepository.findByDoctorId(doctorId);

		return doctorSchedules.stream().map(this::convertToDoctorScheduleDTO).collect(Collectors.toList());

	}

	private DataTablesOutput<DoctorDTO> convertToDataTablesOutputDoctorDTO(DataTablesOutput<Doctor> doctor) {

		DataTablesOutput<DoctorDTO> dataTablesOutputDoctorDTO = new DataTablesOutput<>();

		dataTablesOutputDoctorDTO.setDraw(doctor.getDraw());
		dataTablesOutputDoctorDTO.setError(doctor.getError());
		dataTablesOutputDoctorDTO.setRecordsFiltered(doctor.getRecordsFiltered());
		dataTablesOutputDoctorDTO.setRecordsTotal(doctor.getRecordsTotal());
		dataTablesOutputDoctorDTO.setSearchPanes(doctor.getSearchPanes());
		dataTablesOutputDoctorDTO
				.setData(doctor.getData().stream().map(this::convertToDoctorDTO).collect(Collectors.toList()));
		return dataTablesOutputDoctorDTO;

	}

	private DoctorDTO convertToDoctorDTO(Doctor doctor) {

		List<DoctorAttendance> doctorAttendances = doctorAttendanceRepository.findByDoctorIdAndDate(doctor.getId(),
				Date.valueOf(LocalDate.now()));

		return new DoctorDTO(doctor.getId(), null, doctor.getFirstName(), doctor.getLastName(), doctor.getEmail(),
				doctor.getPhone(), doctor.getDepartment(), null, null, null,
				doctorAttendances.stream().map(this::convertToDoctorAttendanceDTO).collect(Collectors.toList()), null);

	}

	private DoctorAttendanceDTO convertToDoctorAttendanceDTO(DoctorAttendance doctorAttendance) {

		return new DoctorAttendanceDTO(doctorAttendance.getId(), null, doctorAttendance.getLocation(),
				doctorAttendance.getDate(), doctorAttendance.getInTime(), doctorAttendance.getOutTime(), null, null,
				null);

	}

	private DoctorScheduleDTO convertToDoctorScheduleDTO(DoctorSchedule doctorSchedule) {

		return new DoctorScheduleDTO(doctorSchedule.getId(), null, doctorSchedule.getLocation(),
				doctorSchedule.getDayOfWeek(), doctorSchedule.getStartTime(), doctorSchedule.getEndTime(),
				doctorSchedule.getSlot(), doctorSchedule.getDuration(), null, null);

	}
}
