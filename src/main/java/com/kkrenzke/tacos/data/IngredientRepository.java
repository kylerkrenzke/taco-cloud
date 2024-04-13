package com.kkrenzke.tacos.data;

import java.util.List;
import java.util.Optional;

import com.kkrenzke.tacos.Ingredient;

public interface IngredientRepository {
  List<Ingredient> findAll();

  Optional<Ingredient> findById(String id);

  Ingredient save(Ingredient ingredient);
}
