package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ehealthss.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	@Query("select a from Appointment a where a.referenceNo = ?1")
	Appointment findByReferenceNo(String referenceNo);
}
