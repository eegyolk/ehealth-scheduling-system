package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.service.interfaces.AppointmentActivityService;

@Controller
@RequestMapping("appointment-activities")
public class AppointmentActivityController {
	@Autowired
	AppointmentActivityService appointmentActivityService;
}
