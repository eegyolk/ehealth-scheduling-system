package com.ehealthss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Location;
import com.ehealthss.model.LocationAvailability;
import com.ehealthss.repository.LocationAvailabilityRepository;
import com.ehealthss.repository.LocationRepository;
import com.ehealthss.service.ClinicService;

import jakarta.validation.Valid;

@Service
public class ClinicServiceImpl implements ClinicService {

	@Autowired
	LocationRepository locationRepository;
	
	@Autowired
	LocationAvailabilityRepository locationAvailabilityRepository;

	@Override
	public String index(Model model, UserDetails userDetails) {

		String template = "clinic/clinic";

		model.addAttribute("pageTitle", "Clinic");
		model.addAttribute("withCalendarComponent", false);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);
		model.addAttribute("withMapComponent", true);

		return template;

	}

	@Override
	public DataTablesOutput<Location> fetchLocations(@Valid DataTablesInput input) {

		Specification<Location> specification = (Specification<Location>) (root, query, builder) -> {

			Column searchByFieldName = input.getColumns().get(7);
			Column searchByFieldValue = input.getColumns().get(8);

			int fieldNum = searchByFieldName.getSearch().getValue() == "" ? 0
					: Integer.valueOf(searchByFieldName.getSearch().getValue());
			String fieldValue = searchByFieldValue.getSearch().getValue().trim();

			if (fieldNum == 1 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "name"
				 */
				return builder.like(root.get("name"), "%" + fieldValue + "%");

			} else if (fieldNum == 2 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "name"
				 */
				return builder.like(root.get("address"), "%" + fieldValue + "%");

			} else {

				return null;

			}

		};

		return locationRepository.findAll(input, specification);

	}

	@Override
	public List<LocationAvailability> fetchAvailability(int locationId) {

		return locationAvailabilityRepository.findByLocationId(locationId);

	}

}
