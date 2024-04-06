package com.ehealthss.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ehealthss.model.DoctorAttendance;

@Service
public interface DoctorAttendanceService {

	void save(DoctorAttendance doctorAttendance);

	DoctorAttendance findByDoctorIdAndLocationIdAndDate(int doctorId, int locationId, Date date);

}
