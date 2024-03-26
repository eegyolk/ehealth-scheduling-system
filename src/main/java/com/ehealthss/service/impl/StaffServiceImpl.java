package com.ehealthss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehealthss.repository.StaffRepository;
import com.ehealthss.service.StaffService;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired
	StaffRepository staffRepository;
}
