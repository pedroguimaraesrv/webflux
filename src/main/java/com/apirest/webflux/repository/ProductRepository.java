package com.apirest.webflux.repository;

import org.springframework.data.repository.CrudRepository;

import com.apirest.webflux.models.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
	@Override
	void delete(Product deleted);
}
