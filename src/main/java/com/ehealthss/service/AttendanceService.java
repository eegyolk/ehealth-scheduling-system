package com.ehealthss.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.DoctorAttendance;
import com.ehealthss.model.User;

import jakarta.validation.Valid;

@Service
public interface AttendanceService {

	String index(Model model, User user);

	void create(User user, int scheduleId, DoctorAttendance doctorAttendance);

	DataTablesOutput<DoctorAttendance> fetchAttendances(User currentUser, @Valid DataTablesInput input);

}
