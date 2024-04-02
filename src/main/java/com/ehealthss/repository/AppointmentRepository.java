package com.ehealthss.repository;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.Appointment;

import jakarta.validation.Valid;

@Repository
public interface AppointmentRepository extends DataTablesRepository<Appointment, Integer> {

	Appointment findByReferenceNo(String referenceNo);

	DataTablesOutput<Appointment> findAll(@Valid DataTablesInput input, Specification<Appointment> specification);

}
