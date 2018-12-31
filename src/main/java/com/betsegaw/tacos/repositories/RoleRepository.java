package com.betsegaw.tacos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.betsegaw.tacos.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

	 Role findByRole(String role);
	 
}
