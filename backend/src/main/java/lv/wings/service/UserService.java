package lv.wings.service;

import java.util.List;
import lv.wings.dto.request.admin.AdminPasswordDto;
import lv.wings.dto.request.admin.NewUserDetailsDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.users.UserAdminDto;
import lv.wings.dto.response.admin.users.UserDetailsDto;
import lv.wings.model.security.User;

public interface UserService extends CRUDService<User, Integer> {
    List<UserAdminDto> getAllEmployees(String status, String sortBy, String direction);

    void updateLastActivity(Integer id);

    UserDetailsDto getEmployeeData(Integer id);

    BasicMessageDto updateUser(Integer id, UserDetailsDto dto);

    BasicMessageDto updatePassword(Integer id, AdminPasswordDto dto);

    BasicMessageDto createNewEmployee(NewUserDetailsDto dto);
}
