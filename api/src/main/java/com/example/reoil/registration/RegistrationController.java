package com.example.reoil.registration;

import com.example.reoil.GenericResponse;
import com.example.reoil.registration.dtos.UserRegistration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;


@RestController
public class RegistrationController {


  private final RegistrationService registrationService;

  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }


  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(value = "/api/registration")
  public GenericResponse registerUser(@Valid @RequestBody UserRegistration userRegistrationDto,
                                      HttpServletRequest request) {
    return registrationService.registerUser(userRegistrationDto, request);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/api/registration/token")
  public GenericResponse confirmRegistration
      (HttpServletRequest request, @RequestParam("token") String token) {

    Locale locale = request.getLocale();

    return registrationService.confirmRegistration(request.getLocale(), token);
  }

}
