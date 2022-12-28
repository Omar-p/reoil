package com.example.reoil.registration;

import com.example.reoil.GenericResponse;
import com.example.reoil.config.security.SecurityConfig;
import com.example.reoil.registration.dtos.UserRegistration;
import com.example.reoil.domain.security.User;
import com.example.reoil.repositories.RoleRepository;
import com.example.reoil.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = RegistrationController.class)
//@ContextConfiguration(classes = SecurityConfig.class)
@WebMvcTest(controllers = {RegistrationController.class})
@Import(SecurityConfig.class)
class RegistrationControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  RegistrationService registrationService;

  @MockBean
  RoleRepository roleRepository;

  @MockBean
  UserService userService;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MessageSource messageSource;



  UserRegistration requestBody;

  @BeforeEach
  void setup() {
    requestBody = new UserRegistration(
        "omar",
        "omar@email.com",
        "87654321",
        "87654321",
        "01234567891"
    );
  }


  @Test
  void registerUserWithValidParameters() throws Exception {

    final String msg = messageSource.getMessage("registration.success", null, Locale.ENGLISH);
    when(registrationService.registerUser(any(), any())).thenReturn(new GenericResponse(msg, null));

    mockMvc.perform(post("/api/registration")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(requestBody)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message", is(msg)));

  }

  @Test
  void registerWithDuplicateUsername_returnConflict() throws Exception {

    when(userService.findByUsername("omar")).thenReturn(Optional.of(new User()));

    mockMvc.perform(post("/api/registration")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(requestBody)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.data", aMapWithSize(1)))
        .andExpect(jsonPath("$.data.username", containsString("exist")));
  }

  @Test
  void registerWithDuplicateEmail_returnConflict() throws Exception {

    when(userService.findByEmail("omar@email.com")).thenReturn(Optional.of(new User()));

    mockMvc.perform(post("/api/registration")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(requestBody)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.data", aMapWithSize(1)))
        .andExpect(jsonPath("$.data.email", containsString("exist")));
  }

  @Test
  void registerWithInvalidUsername_returnBadRequest() throws Exception {

    requestBody = new UserRegistration(
        "om",
        "omar@email.com",
        "87654321",
        "87654321",
        "01234567891"
    );

    mockMvc.perform(post("/api/registration")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(requestBody)))
        .andExpect(status().isBadRequest());

  }
}