package lv.wings.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import lv.wings.model.security.User;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.UserService;

@Service
public class UserServiceImpl extends AbstractCRUDService<User, Integer> implements UserService {

    public UserServiceImpl(JpaRepository<User, Integer> repository) {
        super(repository, "User", "entity.user");
    }

}
