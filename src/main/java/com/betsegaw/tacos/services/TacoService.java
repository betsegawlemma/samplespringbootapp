package com.betsegaw.tacos.services;

import java.util.Optional;

import com.betsegaw.tacos.domains.Taco;

public interface TacoService {

	public Taco save(Taco ingredient);
	
	public Iterable<Taco> saveAll(Iterable<Taco> tacos);

	Optional<Taco> findById(Long id);

	boolean existsById(Long id);
	
	Iterable<Taco> findAll();

	Iterable<Taco> findAllById(Iterable<Long> ids);

	long count();
	
	void deleteById(Long id);
	
	void delete(Taco taco);
	
	void deleteAll(Iterable<Taco> tacos);

	void deleteAll();
}
