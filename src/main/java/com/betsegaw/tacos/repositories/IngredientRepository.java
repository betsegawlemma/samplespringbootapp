package com.betsegaw.tacos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.betsegaw.tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{
}
