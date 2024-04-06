package com.ehealthss.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.DoctorAttendance;

@Repository
public interface DoctorAttendanceRepository extends JpaRepository<DoctorAttendance, Integer> {

	DoctorAttendance findByDoctorIdAndLocationIdAndDate(int doctorId, int locationId, Date localDate);

}
