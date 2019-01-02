package com.betsegaw.tacos.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.betsegaw.tacos.security.User;

public interface UserService extends UserDetailsService {

	User findUserByUsername(String username);
	void saveUser(User user);
	
}
