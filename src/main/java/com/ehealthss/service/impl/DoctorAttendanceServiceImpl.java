package com.ehealthss.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ehealthss.model.DoctorAttendance;
import com.ehealthss.repository.DoctorAttendanceRepository;
import com.ehealthss.service.DoctorAttendanceService;

import jakarta.validation.Valid;

@Service
public class DoctorAttendanceServiceImpl implements DoctorAttendanceService {

	@Autowired
	DoctorAttendanceRepository doctorAttendanceRepository;

	@Override
	public void save(DoctorAttendance doctorAttendance) {

		doctorAttendanceRepository.save(doctorAttendance);

	}

	@Override
	public DoctorAttendance findByDoctorIdAndLocationIdAndDate(int doctorId, int locationId, Date date) {

		return doctorAttendanceRepository.findByDoctorIdAndLocationIdAndDate(doctorId, locationId, date);

	}

	@Override
	public DataTablesOutput<DoctorAttendance> findAll(@Valid DataTablesInput input,
			Specification<DoctorAttendance> specification) {

		return doctorAttendanceRepository.findAll(input, specification);

	}

	@Override
	public DoctorAttendance getReferenceById(int id) {
		
		return doctorAttendanceRepository.getReferenceById(id);
		
	}
}
