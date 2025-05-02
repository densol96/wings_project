package lv.wings.service;

import lv.wings.dto.request.users.LoginDto;
import lv.wings.dto.request.users.NewUserDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.users.AuthResponseDto;
import lv.wings.dto.response.users.UserSessionInfoDto;

public interface AuthService {
    AuthResponseDto register(NewUserDto dto);

    AuthResponseDto authenticate(LoginDto request);

    UserSessionInfoDto getLoggedInUserInfo();

    BasicMessageDto requestToUnlockAccount(String token);

    BasicMessageDto unlockAccount(String token);
}
