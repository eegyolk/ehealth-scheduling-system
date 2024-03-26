package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.DoctorAttendanceRepository;
import com.ehealthss.service.DoctorAttendanceService;

@Service
public class DoctorAttendanceServiceImpl implements DoctorAttendanceService {
	@Autowired
	DoctorAttendanceRepository doctorAttendanceRepository;
}
