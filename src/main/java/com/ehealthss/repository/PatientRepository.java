package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
