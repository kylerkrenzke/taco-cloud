package com.kkrenzke.tacos.data;

import org.springframework.data.repository.CrudRepository;

import com.kkrenzke.tacos.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}
