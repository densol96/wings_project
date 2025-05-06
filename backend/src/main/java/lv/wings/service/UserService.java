package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.admin.users.UserAdminDto;
import lv.wings.model.security.User;

public interface UserService extends CRUDService<User, Integer> {
    List<UserAdminDto> getAllEmployees(String status, String sortBy, String direction);

    void updateLastActivity(Integer id);
}
