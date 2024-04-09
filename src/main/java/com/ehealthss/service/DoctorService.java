package com.ehealthss.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.bean.DoctorDTO;
import com.ehealthss.bean.DoctorScheduleDTO;

import jakarta.validation.Valid;

@Service
public interface DoctorService {

	String index(Model model, UserDetails userDetails);

	DataTablesOutput<DoctorDTO> findAll(@Valid DataTablesInput input);

	List<DoctorScheduleDTO> fetchSchedules(int doctorId);

}
