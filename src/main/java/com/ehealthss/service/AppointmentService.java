package com.ehealthss.service;

import org.springframework.stereotype.Service;

import com.ehealthss.model.Appointment;

@Service
public interface AppointmentService {

	Appointment save(Appointment appointment);
	
	String generateReferenceNo(int patientId, int doctorId, int locationId);

}
