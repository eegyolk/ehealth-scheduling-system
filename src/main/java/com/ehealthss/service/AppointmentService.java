package com.ehealthss.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.bean.AppointmentActivityDTO;
import com.ehealthss.bean.AppointmentDTO;
import com.ehealthss.bean.CalendarEventRequestDTO;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.enums.DoctorDepartment;

@Service
public interface AppointmentService {

	String index(Model model, UserDetails userDetails);

	Set<Doctor> fetchDoctorsByDepartmentAndLocation(DoctorDepartment doctorDepartment, UserDetails userDetails);

	List<AppointmentDTO> fetchAppointments(UserDetails userDetails, CalendarEventRequestDTO calendarEventRequestDTO);

	AppointmentDTO fetchAppointment(int appointmentId);

	void updateStatus(UserDetails userDetails, int appointmentId, AppointmentActivityDTO appointmentActivityDTO);

}
