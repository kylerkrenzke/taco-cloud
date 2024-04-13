package com.kkrenzke.tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kkrenzke.tacos.Ingredient;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class JdbcIngredientRepository implements IngredientRepository {
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Ingredient> findAll() {
    return jdbcTemplate.query("SELECT id, name, type FROM ingredient", this::mapRowToIngredient);
  }

  @Override
  public Optional<Ingredient> findById(String id) {
    List<Ingredient> results = jdbcTemplate.query("SELECT id, name, type FROM ingredient WHERE id=?",
        this::mapRowToIngredient, id);
    return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
  }

  @Override
  public Ingredient save(Ingredient ingredient) {
    jdbcTemplate.update("INSERT INTO ingredient (id, name, type) VALUES (?, ?, ?)", ingredient.id(), ingredient.name(),
        ingredient.type().toString());
    return ingredient;
  }

  private Ingredient mapRowToIngredient(ResultSet resultSet, int rowNum) throws SQLException {
    return new Ingredient(resultSet.getString("id"), resultSet.getString("name"),
        Ingredient.Type.valueOf(resultSet.getString("type")));
  }
}
