package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehealthss.model.PatientSetting;

public interface PatientSettingRepository extends JpaRepository<PatientSetting, Integer> {

}
