package com.ehealthss.repository;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.enums.AppointmentStatus;

@Repository
public interface AppointmentRepository extends DataTablesRepository<Appointment, Integer> {

	List<Appointment> findByPatientIdAndStatusOrderByCreatedOnDesc(int patientId, AppointmentStatus status);

}
