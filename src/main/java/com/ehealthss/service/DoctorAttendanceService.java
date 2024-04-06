package com.ehealthss.service;

import java.util.Date;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ehealthss.model.DoctorAttendance;

import jakarta.validation.Valid;

@Service
public interface DoctorAttendanceService {

	void save(DoctorAttendance doctorAttendance);

	DoctorAttendance findByDoctorIdAndLocationIdAndDate(int doctorId, int locationId, Date date);

	DataTablesOutput<DoctorAttendance> findAll(@Valid DataTablesInput input, Specification<DoctorAttendance> specification);

}
