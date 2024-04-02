package com.ehealthss.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.User;

import jakarta.validation.Valid;

@Service
public interface BookAppointmentService {

	String index(Model model, User user);
	
	void create(User user, int doctorId, int locationId, Appointment appointment);

	DataTablesOutput<Appointment> fetchAppointments(User currentUser, @Valid DataTablesInput input);

}
