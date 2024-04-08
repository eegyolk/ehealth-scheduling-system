package com.ehealthss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ehealthss.model.Location;
import com.ehealthss.repository.LocationRepository;
import com.ehealthss.service.LocationService;

import jakarta.validation.Valid;

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

	@Override
	public DataTablesOutput<Location> findAll(@Valid DataTablesInput input,
			Specification<Location> specification) {

		return locationRepository.findAll(input, specification);

	}
}
