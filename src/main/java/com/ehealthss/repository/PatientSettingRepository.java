package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.PatientSetting;

@Repository
public interface PatientSettingRepository extends JpaRepository<PatientSetting, Integer> {

}
