package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehealthss.model.AppointmentActivity;

public interface AppointmentActivityRepository extends JpaRepository<AppointmentActivity, Integer> {

}
