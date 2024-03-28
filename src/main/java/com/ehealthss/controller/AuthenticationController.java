package com.ehealthss.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class AuthenticationController {

	@GetMapping("")
	public String index() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(Model uiModel, CsrfToken token,
			@RequestParam(name = "error", required = false) boolean hasError,
			@RequestParam(name = "logout", required = false) boolean isLogout) {

		uiModel.addAttribute("pageTitle", "Login");
		uiModel.addAttribute("hasError", hasError);
		uiModel.addAttribute("isLogout", isLogout);

		return "authentication/login";
	}
}
