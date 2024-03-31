package com.ehealthss.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.enums.DoctorDepartment;

@Service
public interface DoctorService {

	List<Doctor> findByDepartment(DoctorDepartment doctorDepartment);

	Doctor getReferenceById(int id);
	
}
