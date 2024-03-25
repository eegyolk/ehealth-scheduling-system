package com.ehealthss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.DoctorAttendanceRepository;
import com.ehealthss.service.interfaces.DoctorAttendanceService;

@Service
public class DoctorAttendanceServiceImpl implements DoctorAttendanceService {
	@Autowired
	DoctorAttendanceRepository doctorAttendanceRepository;
}
