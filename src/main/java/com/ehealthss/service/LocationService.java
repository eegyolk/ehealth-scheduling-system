package com.ehealthss.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ehealthss.model.Location;

import jakarta.validation.Valid;

@Service
public interface LocationService {

	List<Location> findAll();

	Location getReferenceById(int id);
	
	DataTablesOutput<Location> findAll(@Valid DataTablesInput input, Specification<Location> specification);

}
