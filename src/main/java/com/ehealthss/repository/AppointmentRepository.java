package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	Appointment findByReferenceNo(String referenceNo);
}
