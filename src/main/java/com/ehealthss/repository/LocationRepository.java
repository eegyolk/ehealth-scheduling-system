package com.ehealthss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
