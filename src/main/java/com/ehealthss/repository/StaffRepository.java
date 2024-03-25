package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

}
