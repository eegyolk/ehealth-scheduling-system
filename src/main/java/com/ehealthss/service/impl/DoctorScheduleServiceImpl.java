package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.DoctorScheduleRepository;
import com.ehealthss.service.DoctorScheduleService;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {
	@Autowired
	DoctorScheduleRepository doctorScheduleRepository;
}
