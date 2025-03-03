package lv.wings.service;

import java.net.Authenticator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lv.wings.config.MyUserDetailsMenager;
import lv.wings.model.security.MyAuthority;
import lv.wings.model.security.MyUser;
import lv.wings.repo.security.IMyAuthorityRepo;
import lv.wings.repo.security.IMyUserRepo;
import lv.wings.responses.AuthResponse;

@Service
public class AuthService {

   private final IMyUserRepo userRepo;
   private final IMyAuthorityRepo authorityRepo;
   private final JwtService jwtService;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authManager;

   public AuthService(IMyUserRepo userRepo, IMyAuthorityRepo authorityRepo,
         JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authManager) {
      this.userRepo = userRepo;
      this.authorityRepo = authorityRepo;
      this.jwtService = jwtService;
      this.passwordEncoder = passwordEncoder;
      this.authManager = authManager;
   }

   public AuthResponse resgister(MyUser request) {
      MyUser user = new MyUser();
      MyAuthority authority = authorityRepo.findById(1).orElseThrow(); // ADMIN authority

      user.setUsername(request.getUsername());
      user.setPassword(passwordEncoder.encode(request.getPassword()));
      user.setAuthority(authority);

      userRepo.save(user);

      String token = jwtService.generateToken(user);
      return new AuthResponse(token);
   }

   public AuthResponse authenticate(MyUser request) {
      authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
      MyUser user = userRepo.findByUsername(request.getUsername());
      String token = jwtService.generateToken(user);
      return new AuthResponse(token);
   }

}
