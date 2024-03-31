package com.ehealthss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ehealthss.model.DoctorSchedule;

@Service
public interface DoctorScheduleService {

	List<DoctorSchedule> findByDoctorId(int doctorId);

}
