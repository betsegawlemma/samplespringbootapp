package com.betsegaw.tacos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.betsegaw.tacos.security.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);
	
}
