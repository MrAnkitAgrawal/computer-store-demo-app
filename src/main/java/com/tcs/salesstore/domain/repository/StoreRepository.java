package com.tcs.salesstore.domain.repository;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import com.tcs.salesstore.domain.model.ProductType;

@Transactional
public interface StoreRepository extends CrudRepository<ProductType, Integer> {
	public ProductType findByProductType(String productType);
}
