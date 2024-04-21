package com.ehealthss.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.bean.AppointmentDTO;
import com.ehealthss.bean.CalendarEventRequestDTO;
import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.enums.DoctorDepartment;

import jakarta.validation.Valid;

@Service
public interface AppointmentService {

	String index(Model model, UserDetails userDetails);

	Set<Doctor> fetchDoctorsByDepartmentAndLocation(DoctorDepartment doctorDepartment, UserDetails userDetails);
	
	List<AppointmentDTO> fetchAppointments(UserDetails userDetails, CalendarEventRequestDTO calendarEventRequestDTO);
	
	
	
	
	
	DataTablesOutput<AppointmentDTO> fetchAppointments(UserDetails userDetails, @Valid DataTablesInput input);

	AppointmentDTO fetchAppointment(int appointmentId);

	void updateStatus(UserDetails userDetails, int appointmentId, AppointmentActivity appointmentActivity);

	

}
