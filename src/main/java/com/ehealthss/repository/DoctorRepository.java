package com.ehealthss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.enums.DoctorDepartment;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	
	List<Doctor> findByDepartment(DoctorDepartment doctorDepartment);

}
