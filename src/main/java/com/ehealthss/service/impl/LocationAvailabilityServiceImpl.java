package com.ehealthss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.model.LocationAvailability;
import com.ehealthss.repository.LocationAvailabilityRepository;
import com.ehealthss.service.LocationAvailabilityService;

@Service
public class LocationAvailabilityServiceImpl implements LocationAvailabilityService {
	
	@Autowired
	LocationAvailabilityRepository locationAvailabilityRepository;

	@Override
	public List<LocationAvailability> findByLocationId(int locationId) {
		
		return locationAvailabilityRepository.findByLocationId(locationId);
		
	}
}
