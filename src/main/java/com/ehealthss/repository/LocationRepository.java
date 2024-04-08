package com.ehealthss.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import com.ehealthss.model.Location;

@Repository
public interface LocationRepository extends DataTablesRepository<Location, Integer> {

}
