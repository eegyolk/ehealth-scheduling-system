package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehealthss.model.DoctorAttendance;

public interface DoctorAttendanceRepository extends JpaRepository<DoctorAttendance, Integer> {

}
