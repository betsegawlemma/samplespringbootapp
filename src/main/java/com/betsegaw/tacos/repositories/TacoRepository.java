package com.betsegaw.tacos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.betsegaw.tacos.domains.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long>{
}