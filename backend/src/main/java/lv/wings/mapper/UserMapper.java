package lv.wings.mapper;

import java.util.Set;
import org.mapstruct.Mapper;
import lv.wings.dto.request.users.NewUserDto;
import lv.wings.dto.response.admin.users.UserAdminDto;
import lv.wings.dto.response.users.UserSessionInfoDto;
import lv.wings.model.security.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserSessionInfoDto userToSessionInfo(User user, Set<String> authorities);

    User dtoToNewUser(NewUserDto newUser);

    UserAdminDto userToAdminDto(User user);
}
