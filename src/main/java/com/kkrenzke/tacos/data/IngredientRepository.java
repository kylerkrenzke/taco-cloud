package com.kkrenzke.tacos.data;

import org.springframework.data.repository.CrudRepository;

import com.kkrenzke.tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
