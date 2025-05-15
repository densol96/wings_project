package lv.wings.mapper;

import java.util.Set;
import org.mapstruct.Mapper;
import lv.wings.dto.request.users.NewUserDto;
import lv.wings.dto.response.admin.orders.UserMinDto;
import lv.wings.dto.response.admin.users.UserAdminDto;
import lv.wings.dto.response.admin.users.UserDetailsDto;
import lv.wings.dto.response.users.UserSessionInfoDto;
import lv.wings.model.security.Role;
import lv.wings.model.security.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserSessionInfoDto userToSessionInfo(User user, Set<String> authorities);

    User dtoToNewUser(NewUserDto newUser);

    UserAdminDto userToAdminDto(User user, String status);

    UserDetailsDto usetToDetails(User user);

    UserMinDto toMinUserDto(User user);

    default String role(Role role) {
        return role != null ? role.getName() : null;
    }

    default Integer roleId(Role role) {
        return role != null ? role.getId() : null;
    }
}
