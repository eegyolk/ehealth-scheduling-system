package com.ehealthss.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ehealthss.bean.AppointmentActivityAlertDTO;
import com.ehealthss.bean.AppointmentActivityDTO;
import com.ehealthss.bean.DoctorDTO;
import com.ehealthss.bean.DoctorScheduleDTO;
import com.ehealthss.bean.PatientDTO;
import com.ehealthss.bean.PatientSettingDTO;
import com.ehealthss.bean.StaffDTO;
import com.ehealthss.bean.UserDTO;
import com.ehealthss.model.Appointment;
import com.ehealthss.model.AppointmentActivity;
import com.ehealthss.model.AppointmentActivityAlert;
import com.ehealthss.model.Doctor;
import com.ehealthss.model.Patient;
import com.ehealthss.model.PatientSetting;
import com.ehealthss.model.Staff;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.AppointmentStatus;
import com.ehealthss.repository.AppointmentActivityAlertRepository;
import com.ehealthss.repository.DoctorRepository;
import com.ehealthss.repository.DoctorScheduleRepository;
import com.ehealthss.repository.PatientRepository;
import com.ehealthss.repository.PatientSettingRepository;
import com.ehealthss.repository.StaffRepository;
import com.ehealthss.repository.UserRepository;
import com.ehealthss.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AppointmentActivityAlertRepository appointmentActivityAlertRepository;

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	StaffRepository staffRepository;

	@Autowired
	PatientSettingRepository patientSettingRepository;

	@Autowired
	DoctorScheduleRepository doctorScheduleRepository;

	@Override
	public List<AppointmentActivityAlertDTO> fetchActivityAlerts(UserDetails userDetails) {

		User user = userRepository.findByUsername(userDetails.getUsername());

		List<AppointmentActivityAlert> appointmentActivityAlerts = appointmentActivityAlertRepository
				.findByReceiverIdAndSeenOrderByCreatedOnDesc(user.getId(), false);

		return appointmentActivityAlerts.stream().map(this::convertToAppointmentActivityAlertDTO)
				.collect(Collectors.toList());

	}

	@Override
	public void updateActivityAlert(UserDetails userDetails, int alertId) throws Exception {
		
		User user = userRepository.findByUsername(userDetails.getUsername());
		
		AppointmentActivityAlert appointmentActivityAlert = appointmentActivityAlertRepository.getReferenceById(alertId);
		
		if (user.getId() != appointmentActivityAlert.getReceiver().getId()) {
			throw new Exception("Current user is not allowed to update this activity alert.");
		}
		
		appointmentActivityAlert.setSeen(true);
		appointmentActivityAlertRepository.save(appointmentActivityAlert);
		
	}
	
	@Override
	public void updatePassword(UserDetails userDetails, UserDTO userDTO) throws Exception {

		User user = userRepository.findByUsername(userDetails.getUsername());

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if (!encoder.matches(userDTO.getOldPassword(), user.getPassword())) {
			throw new Exception("Old password doesn't match.");
		}

		user.setPassword(encoder.encode(userDTO.getNewPassword()));
		userRepository.save(user);

	}

	@Override
	public void updatePatient(UserDetails userDetails, PatientDTO patientDTO) {

		User user = userRepository.findByUsername(userDetails.getUsername());
		Patient patient = user.getPatient();

		patient.setFirstName(patientDTO.getFirstName());
		patient.setLastName(patientDTO.getLastName());
		patient.setEmail(patientDTO.getEmail());
		patient.setPhone(patientDTO.getPhone());
		patient.setGender(patientDTO.getGender());
		patient.setBirthDate(patientDTO.getBirthDate());
		patient.setAddress(patientDTO.getAddress());
		patientRepository.save(patient);

	}

	@Override
	public void updateDoctor(UserDetails userDetails, DoctorDTO doctorDTO) {

		User user = userRepository.findByUsername(userDetails.getUsername());
		Doctor doctor = user.getDoctor();

		doctor.setFirstName(doctorDTO.getFirstName());
		doctor.setLastName(doctorDTO.getLastName());
		doctor.setEmail(doctorDTO.getEmail());
		doctor.setPhone(doctorDTO.getPhone());
		doctor.setDepartment(doctorDTO.getDepartment());
		doctorRepository.save(doctor);

	}

	@Override
	public void updateStaff(UserDetails userDetails, StaffDTO staffDTO) {

		User user = userRepository.findByUsername(userDetails.getUsername());
		Staff staff = user.getStaff();

		staff.setLocation(staffDTO.getLocation());
		staff.setFirstName(staffDTO.getFirstName());
		staff.setLastName(staffDTO.getLastName());
		staff.setEmail(staffDTO.getEmail());
		staff.setPhone(staffDTO.getPhone());
		staffRepository.save(staff);

	}

	@Override
	public void updatePatientSetting(UserDetails userDetails, PatientSettingDTO patientSettingDTO) {

		User user = userRepository.findByUsername(userDetails.getUsername());
		PatientSetting patientSetting = user.getPatient().getPatientSetting();

		if (patientSetting == null) {
			patientSetting = new PatientSetting();
			patientSetting.setPatient(user.getPatient());
		}

		patientSetting.setPreferredDoctor(patientSettingDTO.getPreferredDoctor());
		patientSetting.setPreferredLocation(patientSettingDTO.getPreferredLocation());
		patientSetting.setPreferredDayOfWeek(patientSettingDTO.getPreferredDayOfWeek());
		patientSetting.setPreferredTime(patientSettingDTO.getPreferredTime());
		patientSettingRepository.save(patientSetting);

	}

	@Override
	public void updateDoctorSchedule(UserDetails userDetails, DoctorScheduleDTO doctorScheduleDTO) {

		User user = userRepository.findByUsername(userDetails.getUsername());
//		List<DoctorSchedule> doctorSchedue = user.getDoctor().getDoctorSchedules().;

	}

	private AppointmentActivityAlertDTO convertToAppointmentActivityAlertDTO(
			AppointmentActivityAlert appointmentActivityAlert) {

		return new AppointmentActivityAlertDTO(appointmentActivityAlert.getId(),
				this.convertToAppointmentActivityDTO(appointmentActivityAlert.getAppointmentActivity()),
				appointmentActivityAlert.getSender(), appointmentActivityAlert.getReceiver(),
				appointmentActivityAlert.isSeen(), appointmentActivityAlert.getCreatedOn(), null);

	}

	private AppointmentActivityDTO convertToAppointmentActivityDTO(AppointmentActivity appointmentActivity) {

		return new AppointmentActivityDTO(appointmentActivity.getId(), appointmentActivity.getAppointment(),
				appointmentActivity.getUser(), appointmentActivity.getNotes(), appointmentActivity.getStatus(),
				appointmentActivity.getCreatedOn(), 0);

	}

}
