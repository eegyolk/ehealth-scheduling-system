package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.repository.AppointmentActivityRepository;
import com.ehealthss.service.AppointmentActivityService;

@Service
public class AppointmentActivityServiceImpl implements AppointmentActivityService {
	
	@Autowired
	AppointmentActivityRepository appointmentActivityRepository;

	@Override
	public void save(AppointmentActivity appointmentActivity) {
		appointmentActivityRepository.save(appointmentActivity);
	}
	
}
