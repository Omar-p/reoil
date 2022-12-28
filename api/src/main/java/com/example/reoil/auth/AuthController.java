package com.example.reoil.auth;

import com.example.reoil.GenericResponse;
import com.example.reoil.auth.requests.AuthenticationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

  private final AuthenticationService authenticationService;

  @PostMapping("/api/auth")
  public GenericResponse authenticate(@RequestBody AuthenticationRequest authDto) {
    return authenticationService.authenticate(authDto);
  }
}
