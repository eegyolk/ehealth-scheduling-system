package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.service.DoctorScheduleService;

@Controller
@RequestMapping("/doctor-schedules")
public class DoctorScheduleController {
	@Autowired
	DoctorScheduleService doctorScheduleService;
}
