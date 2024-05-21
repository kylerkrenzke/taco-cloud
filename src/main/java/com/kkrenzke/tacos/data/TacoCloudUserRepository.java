package com.kkrenzke.tacos.data;

import org.springframework.data.repository.CrudRepository;

import com.kkrenzke.tacos.TacoCloudUser;

public interface TacoCloudUserRepository extends CrudRepository<TacoCloudUser, Long> {
  TacoCloudUser findByUsername(String username);
}
