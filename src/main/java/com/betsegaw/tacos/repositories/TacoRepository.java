package com.betsegaw.tacos.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.betsegaw.tacos.domains.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>{
}