package lv.wings.controller;

import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lv.wings.dto.request.users.LoginDto;
import lv.wings.dto.request.users.NewUserDto;
import lv.wings.dto.response.users.AuthResponseDto;
import lv.wings.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody NewUserDto request) {
        return ResponseEntity.ok(authService.resgister(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
