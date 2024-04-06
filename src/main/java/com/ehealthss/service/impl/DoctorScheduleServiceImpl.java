package com.ehealthss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.repository.DoctorScheduleRepository;
import com.ehealthss.service.DoctorScheduleService;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {
	
	@Autowired
	DoctorScheduleRepository doctorScheduleRepository;

	@Override
	public List<DoctorSchedule> findByDoctorId(int doctorId) {
		return doctorScheduleRepository.findByDoctorId(doctorId);
	}

	@Override
	public DoctorSchedule getReferenceById(int id) {
		return doctorScheduleRepository.getReferenceById(id);
	}
}
