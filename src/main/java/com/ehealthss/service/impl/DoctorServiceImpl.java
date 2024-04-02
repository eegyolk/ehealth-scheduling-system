package com.ehealthss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.repository.DoctorRepository;
import com.ehealthss.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
	
	@Autowired
	DoctorRepository doctorRepository;

	@Override
	public List<Doctor> findByDepartment(DoctorDepartment doctorDepartment) {
		return doctorRepository.findByDepartment(doctorDepartment);
	}

	@Override
	public Doctor getReferenceById(int id) {
		return doctorRepository.getReferenceById(id);
	}
	
}
