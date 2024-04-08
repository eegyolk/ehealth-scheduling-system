package com.ehealthss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ehealthss.model.LocationAvailability;

@Service
public interface LocationAvailabilityService {

	List<LocationAvailability> findByLocationId(int locationId);

}
