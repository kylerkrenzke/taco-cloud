package tacos.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

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
