package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.PatientRepository;
import com.ehealthss.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	PatientRepository patientRepository;
}
