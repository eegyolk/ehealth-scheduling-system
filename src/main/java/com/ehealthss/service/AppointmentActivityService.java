package com.ehealthss.service;

import org.springframework.stereotype.Service;

import com.ehealthss.model.AppointmentActivity;

@Service
public interface AppointmentActivityService {

	void save(AppointmentActivity appointmentActivity);

}
