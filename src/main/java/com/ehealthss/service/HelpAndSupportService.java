package com.ehealthss.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface HelpAndSupportService {

	String index(Model model, UserDetails userDetails);

}
