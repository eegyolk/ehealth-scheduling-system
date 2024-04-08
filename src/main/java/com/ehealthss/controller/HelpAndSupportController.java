package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.service.HelpAndSupportService;

@Controller
@RequestMapping("/help-and-support")
public class HelpAndSupportController {

	@Autowired
	private final HelpAndSupportService helpAndSupportService;
	
	public HelpAndSupportController(HelpAndSupportService helpAndSupportService) {
		
		this.helpAndSupportService = helpAndSupportService;
		
	}
	
	@GetMapping("")
	public String index(Model model) {

		return helpAndSupportService.index(model);

	}
	
}
