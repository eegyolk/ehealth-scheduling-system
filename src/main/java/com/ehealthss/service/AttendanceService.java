package com.ehealthss.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.DoctorAttendance;
import com.ehealthss.model.User;

@Service
public interface AttendanceService {

	String index(Model model, User user);

	void create(User user, int scheduleId, DoctorAttendance doctorAttendance);

}
