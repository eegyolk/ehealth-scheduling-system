package com.ehealthss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.AppointmentActivityRepository;
import com.ehealthss.service.interfaces.AppointmentActivityService;

@Service
public class AppointmentActivityServiceImpl implements AppointmentActivityService {
	@Autowired
	AppointmentActivityRepository appointmentActivityRepository;
}
