package com.ehealthss.service;

import java.util.Set;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.DoctorDepartment;

import jakarta.validation.Valid;

@Service
public interface BookAppointmentService {

	String index(Model model, User user);
	
	Set<Doctor> fetchDoctorsByDepartmentAndLocation(DoctorDepartment doctorDepartment, int locationId);
	
	Set<DoctorSchedule> fetchDoctorSchedulesByDoctorAndLocation(int doctorId, int locationId);
	
	void create(User user, int doctorId, int locationId, Appointment appointment);

	DataTablesOutput<Appointment> fetchAppointments(User currentUser, @Valid DataTablesInput input);
	
	void cancel(User user, int appointmentId, AppointmentActivity appointmentActivity);
	
}
