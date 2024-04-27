package com.ehealthss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehealthss.bean.AppointmentActivityAlertDTO;
import com.ehealthss.bean.DoctorDTO;
import com.ehealthss.bean.DoctorScheduleDTO;
import com.ehealthss.bean.PatientDTO;
import com.ehealthss.bean.PatientSettingDTO;
import com.ehealthss.bean.StaffDTO;
import com.ehealthss.bean.UserDTO;
import com.ehealthss.repository.AppointmentActivityAlertRepository;
import com.ehealthss.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping(value = "fetch/activity-alerts", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public List<AppointmentActivityAlertDTO> fetchActivityAlerts(@AuthenticationPrincipal UserDetails userDetails)
	{
		
		return userService.fetchActivityAlerts(userDetails);
		
	}
	
	@PostMapping(value = "/update/activity-alert/{alertId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateActivityAlert(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int alertId) throws Exception {

		userService.updateActivityAlert(userDetails, alertId);

	}
	
	@PostMapping(value = "/update/password", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updatePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserDTO userDTO) throws Exception {

		userService.updatePassword(userDetails, userDTO);

	}

	@PostMapping(value = "/update/patient", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updatePatient(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PatientDTO patientDTO) {

		userService.updatePatient(userDetails, patientDTO);

	}
	
	@PostMapping(value = "/update/doctor", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateDoctor(@AuthenticationPrincipal UserDetails userDetails, @RequestBody DoctorDTO doctorDTO) {

		userService.updateDoctor(userDetails, doctorDTO);

	}
	
	@PostMapping(value = "/update/staff", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateStaff(@AuthenticationPrincipal UserDetails userDetails, @RequestBody StaffDTO staffDTO) {

		userService.updateStaff(userDetails, staffDTO);

	}
	
	@PostMapping(value = "/update/patient-setting", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updatePatientSetting(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PatientSettingDTO patientSettingDTO) {

		userService.updatePatientSetting(userDetails, patientSettingDTO);

	}
	
	@PostMapping(value = "/update/doctor-schedule", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void updateDoctorSchedule(@AuthenticationPrincipal UserDetails userDetails, @RequestBody DoctorScheduleDTO doctorScheduleDTO) {

		userService.updateDoctorSchedule(userDetails, doctorScheduleDTO);

	}
}
