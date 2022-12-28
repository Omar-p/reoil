package com.example.reoil.auth;

import com.example.reoil.GenericResponse;
import com.example.reoil.auth.requests.AuthenticationRequest;
import com.example.reoil.auth.responses.AccessTokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationService {
  private final AuthenticationManager authenticationManager;
  private final AccessTokenService accessTokenService;

  public GenericResponse authenticate(AuthenticationRequest authDto) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
    final Authentication authentication = authenticationManager.authenticate(authenticationToken);
    var accessToken = accessTokenService.generateToken(authentication);
    return new GenericResponse("", new AccessTokenResponse(accessToken));
  }
}
