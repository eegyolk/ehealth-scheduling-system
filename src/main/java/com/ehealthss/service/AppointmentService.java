package com.ehealthss.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ehealthss.model.Appointment;

import jakarta.validation.Valid;

@Service
public interface AppointmentService {

	Appointment save(Appointment appointment);
	
	String generateReferenceNo(int patientId, int doctorId, int locationId);

	DataTablesOutput<Appointment> findAll(@Valid DataTablesInput input, Specification<Appointment> specification);
}
