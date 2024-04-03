package com.ehealthss.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.enums.AppointmentStatus;
import com.ehealthss.repository.AppointmentRepository;
import com.ehealthss.service.AppointmentService;

import jakarta.validation.Valid;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Override
	public Appointment save(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	@Override
	public String generateReferenceNo(int patientId, int doctorId, int locationId) {
		Random rand = new Random();
		int low = 10;
		int high = 100;
		int randNum = rand.nextInt(high - low) + low;
		LocalDate dt = LocalDate.now();
		return String.join("", "REF", String.valueOf(dt.getYear()).substring(2), String.valueOf(dt.getMonthValue()),
				String.valueOf(dt.getDayOfMonth()), String.valueOf(dt.getDayOfYear()), String.valueOf(patientId),
				String.valueOf(doctorId), String.valueOf(locationId), String.valueOf(randNum));
	}

	@Override
	public DataTablesOutput<Appointment> findAll(@Valid DataTablesInput input, Specification<Appointment> specification) {
		return appointmentRepository.findAll(input, specification);
	}
	
	@Override
	public List<Appointment> findByPatientIdAndStatusOrderByCreatedOnDesc(int patientId, AppointmentStatus status) {
		return appointmentRepository.findByPatientIdAndStatusOrderByCreatedOnDesc(patientId, status);
	}
}
