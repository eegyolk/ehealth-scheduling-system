package com.ehealthss.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.model.DoctorAttendance;
import com.ehealthss.repository.DoctorAttendanceRepository;
import com.ehealthss.service.DoctorAttendanceService;

@Service
public class DoctorAttendanceServiceImpl implements DoctorAttendanceService {

	@Autowired
	DoctorAttendanceRepository doctorAttendanceRepository;

	@Override
	public void save(DoctorAttendance doctorAttendance) {

		doctorAttendanceRepository.save(doctorAttendance);

	}

	@Override
	public DoctorAttendance findByDoctorIdAndLocationIdAndDate(int doctorId, int locationId, Date localDate) {
		
		return doctorAttendanceRepository.findByDoctorIdAndLocationIdAndDate(doctorId, locationId, localDate);
		
	}
}
