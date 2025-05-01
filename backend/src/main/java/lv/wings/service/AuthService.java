package lv.wings.service;

import lv.wings.dto.request.users.LoginDto;
import lv.wings.dto.request.users.NewUserDto;
import lv.wings.dto.response.users.AuthResponseDto;

public interface AuthService {
    AuthResponseDto register(NewUserDto dto);

    AuthResponseDto authenticate(LoginDto request);
}
