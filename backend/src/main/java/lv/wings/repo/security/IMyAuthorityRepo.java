package lv.wings.repo.security;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.security.MyAuthority;

public interface IMyAuthorityRepo extends CrudRepository<MyAuthority, Integer>{

}
