package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehealthss.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
