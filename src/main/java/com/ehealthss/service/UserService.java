package com.ehealthss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ehealthss.model.User;

@Service
public interface UserService {

	User findByLogin(String username);
	
	List<User> findAllUsers();

	void save(User user);
	
	void delete(User user);
	
}
