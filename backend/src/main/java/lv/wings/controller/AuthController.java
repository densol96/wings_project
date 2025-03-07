package lv.wings.controller;

import org.springframework.web.bind.annotation.RestController;

import lv.wings.model.security.MyUser;
// import lv.wings.responses.AuthResponse;
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

    // @PostMapping("/register")
    // public ResponseEntity<AuthResponse> register(@RequestBody MyUser request) {
    // return ResponseEntity.ok(authService.resgister(request));
    // }

    // @PostMapping("/login")
    // public ResponseEntity<AuthResponse> login(@RequestBody MyUser request) {
    // return ResponseEntity.ok(authService.authenticate(request));
    // }

}
