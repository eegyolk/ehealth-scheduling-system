package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.LocationRepository;
import com.ehealthss.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	@Autowired
	LocationRepository locationRepository;
}
