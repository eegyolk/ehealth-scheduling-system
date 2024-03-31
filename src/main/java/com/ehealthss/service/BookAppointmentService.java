package com.ehealthss.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.User;

@Service
public interface BookAppointmentService {

	String index(Model model, User user);
	
	void create(User user, int doctorId, int locationId, Appointment appointment);

}
