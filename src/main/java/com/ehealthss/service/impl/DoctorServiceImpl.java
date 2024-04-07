package com.ehealthss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.Doctor;
import com.ehealthss.model.User;
import com.ehealthss.model.enums.DoctorDepartment;
import com.ehealthss.repository.DoctorRepository;
import com.ehealthss.service.DoctorService;

import jakarta.validation.Valid;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepository doctorRepository;

	@Override
	public List<Doctor> findByDepartment(DoctorDepartment doctorDepartment) {
		return doctorRepository.findByDepartment(doctorDepartment);
	}

	@Override
	public Doctor getReferenceById(int id) {
		return doctorRepository.getReferenceById(id);
	}

	@Override
	public String index(Model model, User user) {

		String template = "doctor/doctor";

		model.addAttribute("pageTitle", "Doctor");
		model.addAttribute("withCalendarComponent", false);
		model.addAttribute("withFontAwesome", true);
		model.addAttribute("withTableComponent", true);

		DoctorDepartment[] doctorDepartments = DoctorDepartment.class.getEnumConstants();
		model.addAttribute("doctorDepartments", doctorDepartments);

		return template;

	}

	@Override
	public DataTablesOutput<Doctor> findAll(User user, @Valid DataTablesInput input) {

		Specification<Doctor> specification = (Specification<Doctor>) (root, query, builder) -> {

			Column searchByFieldName = input.getColumns().get(7);
			Column searchByFieldValue = input.getColumns().get(8);

			int fieldNum = searchByFieldName.getSearch().getValue() == "" ? 0
					: Integer.valueOf(searchByFieldName.getSearch().getValue());
			String fieldValue = searchByFieldValue.getSearch().getValue().trim();

			if (fieldNum == 1 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "first_name" or
				 * "last_name" in table "doctor"
				 */
				return builder.or(builder.like(root.get("firstName"), "%" + fieldValue + "%"),
						builder.like(root.get("lastName"), "%" + fieldValue + "%"));

			} else if (fieldNum == 2 && fieldValue.length() > 0) {

				/**
				 * Will produce additional criteria for filtering data with "department" in
				 * table "doctor"
				 */
				return builder.equal(root.get("department"), fieldValue);

			} else {
				
				return null;
				
			}

		};
		
		return doctorRepository.findAll(input, specification);

	}

}
