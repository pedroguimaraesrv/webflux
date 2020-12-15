package com.apirest.webflux.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.apirest.webflux.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}
