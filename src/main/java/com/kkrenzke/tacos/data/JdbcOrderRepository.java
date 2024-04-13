package com.kkrenzke.tacos.data;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kkrenzke.tacos.Ingredient;
import com.kkrenzke.tacos.Taco;
import com.kkrenzke.tacos.TacoOrder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository("jdbcOrderRepository")
public class JdbcOrderRepository {
  private final JdbcOperations jdbcOperations;

  @Transactional
  public TacoOrder save(TacoOrder order) {
    PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
        "INSERT INTO taco_order (delivery_name, "
            + "delivery_street, delivery_city, delivery_state, delivery_zip, cc_number, cc_expiration, cc_cvv, placed_at) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
        Types.VARCHAR, Types.VARCHAR);
    factory.setReturnGeneratedKeys(true);
    order.setPlacedAt(new Date());
    PreparedStatementCreator creator = factory.newPreparedStatementCreator(Arrays.asList(order.getDeliveryName(),
        order.getDeliveryStreet(), order.getDeliveryCity(), order.getDeliveryState(), order.getDeliveryZip(),
        order.getCcNumber(), order.getCcExpiration(), order.getCcCVV(), order.getPlacedAt()));
    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcOperations.update(creator, keyHolder);
    Number key = keyHolder.getKey();
    if (key == null) {
      throw new RuntimeException("Order id is null");
    }
    long orderId = key.longValue();
    order.setId(orderId);
    List<Taco> tacos = order.getTacos();
    for (int i = 0; i < tacos.size(); i++) {
      saveTaco(orderId, i, tacos.get(i));
    }
    return order;
  }

  private long saveTaco(long orderId, int orderKey, Taco taco) {
    taco.setCreatedAt(new Date());
    PreparedStatementCreatorFactory statementCreatorFactory = new PreparedStatementCreatorFactory("INSERT INTO taco " +
        "(name, created_at, taco_order, taco_order_key) VALUES (?, ?, ?, ?)", Types.VARCHAR, Types.VARCHAR,
        Types.VARCHAR, Types.VARCHAR);
    statementCreatorFactory.setReturnGeneratedKeys(true);
    PreparedStatementCreator statementCreator = statementCreatorFactory
        .newPreparedStatementCreator(Arrays.asList(taco.getName(), taco.getCreatedAt(), orderId, orderKey));
    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcOperations.update(statementCreator, keyHolder);
    Number tacoKey = keyHolder.getKey();
    if (tacoKey == null) {
      throw new RuntimeException("Generated taco key is null");
    }
    long tacoId = tacoKey.longValue();
    taco.setId(tacoId);
    saveIngredientRefs(tacoId, taco.getIngredients());
    return tacoId;
  }

  private void saveIngredientRefs(long tacoId, List<Ingredient> ingredientRefs) {
    int key = 0;
    for (Ingredient ref : ingredientRefs) {
      jdbcOperations.update("INSERT INTO ingredients_ref (ingredient, taco, taco_key) VALUES (?, ?, ?)",
          ref.getId(), tacoId, key++);
    }
  }
}
