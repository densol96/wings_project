package lv.wings.controller;

import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.request.users.EmailDto;
import lv.wings.dto.request.users.LoginDto;
import lv.wings.dto.request.users.NewUserDto;
import lv.wings.dto.request.users.PasswordDto;
import lv.wings.dto.request.users.ResetPasswordDto;
import lv.wings.dto.request.users.UsernameDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.users.AuthResponseDto;
import lv.wings.dto.response.users.UserSessionInfoDto;
import lv.wings.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody NewUserDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/user-data")
    public ResponseEntity<UserSessionInfoDto> getLoggedInUserInfo() {
        return ResponseEntity.ok(authService.getLoggedInUserInfo());
    }

    @PostMapping("/request-account-unlock/{token}")
    public ResponseEntity<BasicMessageDto> requestToUnlockAccount(@PathVariable String token) {
        return ResponseEntity.ok(authService.requestToUnlockAccount(token));
    }

    @PostMapping("/unlock-account/{token}")
    public ResponseEntity<BasicMessageDto> unlockAccount(@PathVariable String token) {
        return ResponseEntity.ok(authService.unlockAccount(token));
    }

    @PostMapping("/request-reset-password")
    public ResponseEntity<BasicMessageDto> requestToResetPassword(@Valid @RequestBody UsernameDto usernameDto) {
        return ResponseEntity.ok(authService.requestToResetPassword(usernameDto));
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<BasicMessageDto> resetPassword(@PathVariable String token, @Valid @RequestBody ResetPasswordDto dto) {
        return ResponseEntity.ok(authService.resetPassword(token, dto));
    }

    @PatchMapping("/change-email")
    public ResponseEntity<BasicMessageDto> changeEmail(@Valid @RequestBody EmailDto emailDto) {
        return ResponseEntity.ok(authService.changeEmail(emailDto));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<BasicMessageDto> changePassword(@Valid @RequestBody PasswordDto passwordDto) {
        return ResponseEntity.ok(authService.changePassword(passwordDto));
    }
}
