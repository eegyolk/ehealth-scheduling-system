package com.ehealthss.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.bean.DoctorAttendanceDTO;
import com.ehealthss.model.DoctorAttendance;

import jakarta.validation.Valid;

@Service
public interface AttendanceService {

	String index(Model model, UserDetails userDetails);

	void create(UserDetails userDetails, int scheduleId, DoctorAttendance doctorAttendance);

	DataTablesOutput<DoctorAttendanceDTO> fetchAttendances(UserDetails userDetails, @Valid DataTablesInput input);

	void update(int attendanceId, DoctorAttendance doctorAttendance);

}
