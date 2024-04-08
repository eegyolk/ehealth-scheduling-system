package com.ehealthss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.LocationAvailability;

@Repository
public interface LocationAvailabilityRepository extends JpaRepository<LocationAvailability, Integer> {

	List<LocationAvailability> findByLocationId(int locationId);

}
