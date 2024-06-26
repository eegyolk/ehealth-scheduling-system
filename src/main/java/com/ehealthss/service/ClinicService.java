package com.ehealthss.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Location;
import com.ehealthss.model.LocationAvailability;

import jakarta.validation.Valid;

@Service
public interface ClinicService {

	String index(Model model, UserDetails userDetails);

	DataTablesOutput<Location> fetchLocations(@Valid DataTablesInput input);

	List<LocationAvailability> fetchAvailability(int locationId);

}
