package com.ehealthss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ehealthss.model.Location;

@Service
public interface LocationService {

	List<Location> findAll();

	Location getReferenceById(int id);

}
