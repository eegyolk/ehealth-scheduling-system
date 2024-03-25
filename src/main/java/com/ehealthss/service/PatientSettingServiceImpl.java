package com.ehealthss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.PatientSettingRepository;
import com.ehealthss.service.interfaces.PatientSettingService;

@Service
public class PatientSettingServiceImpl implements PatientSettingService {
	@Autowired
	PatientSettingRepository patientSettingRepository;
}
