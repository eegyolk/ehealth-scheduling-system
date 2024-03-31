package com.ehealthss.service;

import org.springframework.stereotype.Service;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.User;

@Service
public interface BookAppointmentService {

	String index(User user);
	
	void create(User user, int doctorId, int locationId, Appointment appointment);

}
