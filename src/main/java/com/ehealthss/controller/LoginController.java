package com.ehealthss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ehealthss.service.LoginService;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@GetMapping("")
	public String index() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(Model model, CsrfToken token,
			@RequestParam(name = "error", required = false) boolean hasError,
			@RequestParam(name = "logout", required = false) boolean isLogout) {

		return loginService.index(model, hasError, isLogout);
		
	}
}
