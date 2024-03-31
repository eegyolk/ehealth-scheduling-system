package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehealthss.model.Appointment;
import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.Location;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.AppointmentStatus;
import com.ehealthss.service.AppointmentActivityService;
import com.ehealthss.service.AppointmentService;
import com.ehealthss.service.BookAppointmentService;
import com.ehealthss.service.DoctorService;
import com.ehealthss.service.LocationService;

@Service
public class BookAppointmentServiceImpl implements BookAppointmentService {

	@Autowired
	private final DoctorService doctorService;
	private final LocationService locationService;
	private final AppointmentService appointmentService;
	private final AppointmentActivityService appointmentActivityService;
	
	public BookAppointmentServiceImpl(DoctorService doctorService, LocationService locationService, AppointmentService appointmentService, AppointmentActivityService appointmentActivityService) {
		this.doctorService = doctorService;
		this.locationService = locationService;
		this.appointmentService = appointmentService;
		this.appointmentActivityService = appointmentActivityService;
	}
	
	@Override
	public String index(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional
	public void create(User user, int doctorId, int locationId, Appointment appointment) {
		Doctor doctor = doctorService.getReferenceById(doctorId);
		Location location = locationService.getReferenceById(locationId);

		appointment.setPatient(user.getPatient());
		appointment.setDoctor(doctor);
		appointment.setLocation(location);
		appointment.setReferenceNo(
				appointmentService.generateReferenceNo(user.getPatient().getId(), doctorId, locationId));
		appointment.setStatus(AppointmentStatus.PENDING);
		Appointment newAppointment = appointmentService.save(appointment);
		
		AppointmentActivity appointmentActivity = new AppointmentActivity(newAppointment, user, null, AppointmentStatus.PENDING);
		appointmentActivityService.save(appointmentActivity);
	}

}
