package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.LocationAvailabilityRepository;
import com.ehealthss.service.LocationAvailabilityService;

@Service
public class LocationAvailabilityServiceImpl implements LocationAvailabilityService {
	@Autowired
	LocationAvailabilityRepository locationAvailabilityRepository;
}
