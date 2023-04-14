package edu.tanta.fci.reoil.user.registration;

import edu.tanta.fci.reoil.user.model.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.flyway.enabled=false",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb"
})
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class RegistrationControllerTest {

  @MockBean
  private RegistrationService registrationService;




  @Autowired
  private MockMvc mvc;

  private RegistrationRequest validRequest = new RegistrationRequest(
      "omar",
      "omar.plus.077@gmail.com",
      "thisIsAVeryStrong!*Password",
      "thisIsAVeryStrong!*Password",
      "01026641632"
  );

  @Test
  void givenValidUserDataItShouldRegister() throws Exception {



    mvc.perform(MockMvcRequestBuilders.post("/api/v1/registration")
            .contentType(APPLICATION_JSON)
            .content(
                """
{
    "username":  "omar",
    "email": "omar.plus.077@gmail.com",
    "password": "thisIsAVeryStrong!*Password",
    "passwordConfirmation": "thisIsAVeryStrong!*Password",
    "phoneNumber": "01026641632"
}
"""
        )).andExpect(status().isCreated())
        .andDo(
            document(
                "registration",
                requestFields(
                    fieldWithPath("username").description("unique username. min 4 chars"),
                    fieldWithPath("email").description("must be a valid email address"),
                    fieldWithPath("password").description("Password must contain at least 10 characters, 1 upper case, 1 lower case, 1 digit and 1 special character"),
                    fieldWithPath("passwordConfirmation").description("must match the password field"),
                    fieldWithPath("phoneNumber").description("valid phone number")
                )
            )
        );
  }

  @Test
  void givenValidRegistrationTokenItShouldEnableUser() throws Exception {
    var token = UUID.randomUUID().toString();


    mvc.perform(RestDocumentationRequestBuilders.get("/api/v1/registration")
          .param("token", token))
      .andExpect(status().isOk())
      .andDo(
          document(
              "registration-verify",
              queryParameters(
                  parameterWithName("token").description("registration token"))
          )
      );
  }





}