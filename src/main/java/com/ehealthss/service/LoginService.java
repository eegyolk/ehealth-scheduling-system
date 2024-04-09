package com.ehealthss.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface LoginService {

	String index(Model model, boolean hasError, boolean isLogout);

}
