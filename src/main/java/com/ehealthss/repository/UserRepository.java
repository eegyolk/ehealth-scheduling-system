package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehealthss.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
