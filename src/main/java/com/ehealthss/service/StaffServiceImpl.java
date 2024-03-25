package com.ehealthss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.StaffRepository;
import com.ehealthss.service.interfaces.StaffService;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired
	StaffRepository staffRepository;
}
