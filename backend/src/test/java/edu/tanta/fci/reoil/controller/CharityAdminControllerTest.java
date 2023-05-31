package edu.tanta.fci.reoil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tanta.fci.reoil.config.JwtDelegatedAuthenticationEntryPoint;
import edu.tanta.fci.reoil.config.SecurityConfig;
import edu.tanta.fci.reoil.model.NewCharity;
import edu.tanta.fci.reoil.service.CharityAdminService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@AutoConfigureRestDocs
@WebMvcTest(controllers = CharityAdminController.class)
@Import({SecurityConfig.class, JwtDelegatedAuthenticationEntryPoint.class})
class CharityAdminControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;


  @MockBean
  AuthenticationManager authenticationManager;



  @MockBean
  CharityAdminService charityAdminService;

  @Test
  void addNewCharityShouldReturn201() throws Exception {
    NewCharity newCharity = new NewCharity();
    newCharity.setName("مؤسسة مجدى يعقوب للقلب");
    newCharity.setDescription("مؤسسة خيرية");
    newCharity.setAbout("مؤسسة خيرية");
    newCharity.setPhone("01000000000");
    newCharity.setSite("https://www.google.com");

    BDDMockito.given(charityAdminService.addCharity(any(NewCharity.class)))
        .willReturn(1L);

    mockMvc.perform(post("/api/v1/charities")
            .contentType(MediaType.APPLICATION_JSON)
        .with(jwt().jwt((jwt) -> jwt.claim("scope", "ADMIN")))
            .content(objectMapper.writeValueAsString(newCharity)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.header().exists("Location"))
        .andExpect(MockMvcResultMatchers.header().string("Location", CoreMatchers.containsString("/api/v1/charities/1")))
        .andDo(MockMvcRestDocumentation.document("addNewCharity"));
  }

  @Test
  void addNewCharityWithoutAuthenticationShouldReturn401() throws Exception {
    NewCharity newCharity = new NewCharity();
    newCharity.setName("مؤسسة مجدى يعقوب للقلب");
    newCharity.setDescription("مؤسسة خيرية");
    newCharity.setAbout("مؤسسة خيرية");
    newCharity.setPhone("01000000000");
    newCharity.setSite("https://www.google.com");

    BDDMockito.given(charityAdminService.addCharity(any(NewCharity.class)))
        .willReturn(1L);

    mockMvc.perform(post("/api/v1/charities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newCharity)))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        .andDo(MockMvcRestDocumentation.document("addNewCharity401"));
  }

  @Test
  void addNewCharityAsUserShouldReturn403() throws Exception {

    NewCharity newCharity = new NewCharity();
    newCharity.setName("مؤسسة مجدى يعقوب للقلب");
    newCharity.setDescription("مؤسسة خيرية");
    newCharity.setAbout("مؤسسة خيرية");
    newCharity.setPhone("01000000000");
    newCharity.setSite("https://www.google.com");

    BDDMockito.verifyNoInteractions(charityAdminService);

    mockMvc.perform(post("/api/v1/charities")
        .contentType(MediaType.APPLICATION_JSON)
        .with(jwt().jwt((jwt) -> jwt.claim("scope", "USER")))
        .content(objectMapper.writeValueAsString(newCharity)))
        .andExpect(MockMvcResultMatchers.status().isForbidden())
        .andDo(MockMvcRestDocumentation.document("addNewCharityAsUser"));
  }
}