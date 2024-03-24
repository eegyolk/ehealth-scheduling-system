package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehealthss.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
