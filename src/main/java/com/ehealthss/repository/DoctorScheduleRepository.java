package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehealthss.model.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {

}
