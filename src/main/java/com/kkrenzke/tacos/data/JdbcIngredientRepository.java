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
@Repository("jdbcIngredientRepository")
public class JdbcIngredientRepository {
  private JdbcTemplate jdbcTemplate;

  public List<Ingredient> findAll() {
    return jdbcTemplate.query("SELECT id, name, type FROM ingredient", this::mapRowToIngredient);
  }

  public Optional<Ingredient> findById(String id) {
    List<Ingredient> results = jdbcTemplate.query("SELECT id, name, type FROM ingredient WHERE id=?",
        this::mapRowToIngredient, id);
    return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
  }

  public Ingredient save(Ingredient ingredient) {
    jdbcTemplate.update("INSERT INTO ingredient (id, name, type) VALUES (?, ?, ?)", ingredient.getId(),
        ingredient.getName(), ingredient.getType().toString());
    return ingredient;
  }

  private Ingredient mapRowToIngredient(ResultSet resultSet, int rowNum) throws SQLException {
    return new Ingredient(resultSet.getString("id"), resultSet.getString("name"),
        Ingredient.Type.valueOf(resultSet.getString("type")));
  }
}
