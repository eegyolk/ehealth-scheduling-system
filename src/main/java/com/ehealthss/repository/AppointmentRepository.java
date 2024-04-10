package com.ehealthss.repository;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.enums.AppointmentStatus;

import jakarta.validation.Valid;

@Repository
public interface AppointmentRepository extends DataTablesRepository<Appointment, Integer> {

	List<Appointment> findByPatientIdAndStatusOrderByCreatedOnDesc(int patientId, AppointmentStatus status);

}
