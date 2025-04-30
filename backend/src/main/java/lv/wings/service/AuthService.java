package lv.wings.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import lv.wings.config.security.MyUserDetails;
import lv.wings.dto.request.users.LoginDto;
import lv.wings.dto.request.users.NewUserDto;
import lv.wings.dto.response.users.AuthResponseDto;
import lv.wings.model.security.User;
import lv.wings.repo.UserRepository;
import lv.wings.repo.security.IMyAuthorityRepo;

@Service
public class AuthService {

   private final UserRepository userRepo;
   private final IMyAuthorityRepo authorityRepo;
   private final JwtService jwtService;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authManager;

   public AuthService(UserRepository userRepo, IMyAuthorityRepo authorityRepo,
         JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authManager) {
      this.userRepo = userRepo;
      this.authorityRepo = authorityRepo;
      this.jwtService = jwtService;
      this.passwordEncoder = passwordEncoder;
      this.authManager = authManager;
   }

   public AuthResponseDto resgister(NewUserDto dto) {
      User user = User.builder()
            .email(dto.getEmail())
            .username(dto.getUsername())
            .password(passwordEncoder.encode(dto.getPassword())).build();
      userRepo.save(user);
      return new AuthResponseDto(jwtService.generateToken(user));
   }

   public AuthResponseDto authenticate(LoginDto request) {
      Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                  request.getUsername(),
                  request.getPassword()));

      MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
      return new AuthResponseDto(jwtService.generateToken(userDetails.getUser()));
   }

}
