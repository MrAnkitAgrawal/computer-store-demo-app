package com.tcs.salesstore.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.tcs.salesstore.domain.model.ManufactureDetails;

public interface ManufacturerRepository extends CrudRepository<ManufactureDetails, Integer> {
	public ManufactureDetails findByManufacturerName(String manufacturer);
}
