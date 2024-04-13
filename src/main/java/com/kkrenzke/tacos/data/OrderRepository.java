package com.kkrenzke.tacos.data;

import com.kkrenzke.tacos.TacoOrder;

public interface OrderRepository {
  TacoOrder save(TacoOrder order);
}
