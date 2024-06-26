package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehealthss.service.HelpAndSupportService;

@Controller
@RequestMapping("/help-and-support")
public class HelpAndSupportController {

	@Autowired
	private HelpAndSupportService helpAndSupportService;
	
	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		return helpAndSupportService.index(model, userDetails);

	}
	
}
