package com.ehealthss.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ehealthss.bean.DoctorDTO;
import com.ehealthss.bean.DoctorScheduleDTO;
import com.ehealthss.bean.PatientDTO;
import com.ehealthss.bean.PatientSettingDTO;
import com.ehealthss.bean.StaffDTO;
import com.ehealthss.bean.UserDTO;

@Service
public interface UserService {

	void updatePassword(UserDetails userDetails, UserDTO userDTO) throws Exception;

	void updatePatient(UserDetails userDetails, PatientDTO patientDTO);

	void updateDoctor(UserDetails userDetails, DoctorDTO doctorDTO);

	void updateStaff(UserDetails userDetails, StaffDTO staffDTO);

	void updatePatientSetting(UserDetails userDetails, PatientSettingDTO patientSettingDTO);

	void updateDoctorSchedule(UserDetails userDetails, DoctorScheduleDTO doctorScheduleDTO);

}
