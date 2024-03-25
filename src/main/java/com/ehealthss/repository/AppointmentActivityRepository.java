package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.AppointmentActivity;

@Repository
public interface AppointmentActivityRepository extends JpaRepository<AppointmentActivity, Integer> {

}
