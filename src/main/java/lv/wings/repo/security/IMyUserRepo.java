package lv.wings.repo.security;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.security.MyUser;

public interface IMyUserRepo extends CrudRepository<MyUser, Integer>{

	MyUser findByUsername(String username);

}
