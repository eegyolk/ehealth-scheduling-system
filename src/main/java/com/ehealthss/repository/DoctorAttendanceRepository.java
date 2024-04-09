package com.ehealthss.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.DoctorAttendance;

import jakarta.validation.Valid;

@Repository
public interface DoctorAttendanceRepository extends DataTablesRepository<DoctorAttendance, Integer> {

	DoctorAttendance findByDoctorIdAndLocationIdAndDate(int doctorId, int locationId, Date date);
	
	DataTablesOutput<DoctorAttendance> findAll(@Valid DataTablesInput input, Specification<DoctorAttendance> specification);
	
	List<DoctorAttendance> findByDoctorIdAndDate(int doctorId, Date date);

}
