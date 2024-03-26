package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.DoctorRepository;
import com.ehealthss.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	DoctorRepository doctorRepository;
}
