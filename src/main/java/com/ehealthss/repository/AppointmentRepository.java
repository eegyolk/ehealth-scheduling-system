package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehealthss.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	Appointment findByReferenceNo(String referenceNo);
}
