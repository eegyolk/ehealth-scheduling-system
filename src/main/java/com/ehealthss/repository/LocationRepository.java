package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehealthss.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
