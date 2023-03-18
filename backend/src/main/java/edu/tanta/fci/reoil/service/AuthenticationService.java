package edu.tanta.fci.reoil.service;

import edu.tanta.fci.reoil.user.model.Login;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {
  private final AuthenticationManager authenticationManager;
  private final AccessTokenService accessTokenService;

  public AuthenticationService(AuthenticationManager authenticationManager, AccessTokenService accessTokenService) {
    this.authenticationManager = authenticationManager;
    this.accessTokenService = accessTokenService;
  }

  public Map<String, String> authenticate(Login login) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());
    final Authentication authentication = authenticationManager.authenticate(authenticationToken);
    var accessToken = accessTokenService.generateToken(authentication);
    return Map.of("accessToken", accessToken);
  }
}

