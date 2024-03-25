package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.service.interfaces.DoctorService;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
	@Autowired
	DoctorService doctorService;
}
