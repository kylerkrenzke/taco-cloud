package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.TacoCloudUser;

public interface TacoCloudUserRepository extends CrudRepository<TacoCloudUser, Long> {
  TacoCloudUser findByUsername(String username);
}
