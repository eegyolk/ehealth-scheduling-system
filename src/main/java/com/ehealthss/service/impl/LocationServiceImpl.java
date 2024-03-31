package com.ehealthss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.model.Location;
import com.ehealthss.repository.LocationRepository;
import com.ehealthss.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	LocationRepository locationRepository;

	@Override
	public List<Location> findAll() {
		return locationRepository.findAll();
	}

	@Override
	public Location getReferenceById(int id) {
		return locationRepository.getReferenceById(id);
	}

}
