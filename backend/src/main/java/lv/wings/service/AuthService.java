package lv.wings.service;

import lv.wings.dto.request.users.EmailDto;
import lv.wings.dto.request.users.LoginDto;
import lv.wings.dto.request.users.PasswordDto;
import lv.wings.dto.request.users.ResetPasswordDto;
import lv.wings.dto.request.users.UsernameDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.users.AuthResponseDto;
import lv.wings.dto.response.users.UserSessionInfoDto;

public interface AuthService {

    AuthResponseDto authenticate(LoginDto request);

    UserSessionInfoDto getLoggedInUserInfo();

    BasicMessageDto requestToUnlockAccount(String token);

    BasicMessageDto unlockAccount(String token);

    BasicMessageDto requestToResetPassword(UsernameDto usernameDto);

    BasicMessageDto resetPassword(String resetPasswordToken, ResetPasswordDto dto);

    BasicMessageDto changeEmail(EmailDto emailDto);

    BasicMessageDto changePassword(PasswordDto dto);
}
