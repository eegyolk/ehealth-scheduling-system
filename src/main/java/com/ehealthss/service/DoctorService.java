package com.ehealthss.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.DoctorDepartment;

import jakarta.validation.Valid;

@Service
public interface DoctorService {

	List<Doctor> findByDepartment(DoctorDepartment doctorDepartment);

	Doctor getReferenceById(int id);

	String index(Model model, User user);
	
	DataTablesOutput<Doctor> findAll(User user, @Valid DataTablesInput input);
	
}
