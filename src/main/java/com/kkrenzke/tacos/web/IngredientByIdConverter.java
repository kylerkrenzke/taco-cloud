package com.kkrenzke.tacos.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.kkrenzke.tacos.Ingredient;
import com.kkrenzke.tacos.data.IngredientRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
  private final IngredientRepository ingredientRepository;

  @Override
  @Nullable
  public Ingredient convert(@NonNull String id) {
    return ingredientRepository.findById(id).orElse(null);
  }
}
