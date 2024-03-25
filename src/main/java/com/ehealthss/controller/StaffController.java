package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.service.interfaces.StaffService;

@Controller
@RequestMapping("/staffs")
public class StaffController {
	@Autowired
	StaffService staffService;
}
