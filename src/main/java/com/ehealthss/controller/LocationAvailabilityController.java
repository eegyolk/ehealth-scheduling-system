package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.service.interfaces.LocationAvailabilityService;

@Controller
@RequestMapping("/location-availabilities")
public class LocationAvailabilityController {
	@Autowired
	LocationAvailabilityService locationAvailabilityService;
}
