package edu.tanta.fci.reoil.controller;

import edu.tanta.fci.reoil.model.Charity;
import edu.tanta.fci.reoil.model.SelectedCharity;
import edu.tanta.fci.reoil.service.CharityService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@AutoConfigureRestDocs
@WebMvcTest(CharityController.class)
class CharityControllerTest {


  @MockBean
  CharityService charityService;

  @Autowired
  MockMvc mvc;

  @Test
  void itShouldReturnCharities() throws Exception {
    BDDMockito.when(charityService.getCharities()).thenReturn(List.of(new Charity(1L, "مؤسسة مجدى يعقوب للقلب", "مؤسسة خيرية", 0L)));
    mvc
        .perform(RestDocumentationRequestBuilders.get("/api/v1/charities")
            .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoib21hciIsImV4cCI6MTY3OTcyMTY0MSwiaWF0IjoxNjc5NzE4MDQxLCJzY29wZSI6InVzZXIuaW5mby5yZWFkIn0.0If6Qo5ZAMcq4TXnkEkNgpzHP60NEoTqUSV6Di1IgQ8eVR7Illu27ppvBBXGImw5_3oWXcT89XWH6sGEbyyg5wcxXBh_yLX-n4bi4xE5_MgSGZDqBfAGepZLczD7LzqbEpAC74Tajj05F2utI6TZkzviy8YDa60rE2IlWQoaR9pfwnhVJjsNnPW_T82cJyjT5hmVzZ7CScy5ZtSSL4QgslPy0IWixTRPna5GhA2GIMc_nfPuo7ePw6ggxXk1GSXliV5RPRp40EUpMBBxDWP362LSr5GSREWw2dF7QiPlfhH-uq46e5vQQZ8M28wjZYIOYpsW7ntaOBnNp9hx0suYNQ")
            .with(jwt().jwt((jwt) -> jwt.claim("scope", "USER"))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(
            """
                [
                  {
                    "id": 1,
                    "name": "مؤسسة مجدى يعقوب للقلب",
                    "description": "مؤسسة خيرية",
                    "currentPoints": 0
                  }
                ]
                """
        )).andDo(MockMvcRestDocumentation.document("charities", HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName("Authorization").description("JWT token"))));
  }

  @Test
  void getACharityByIdShouldReturnDetailsAboutIt() throws Exception {
    BDDMockito.when(charityService.getCharity(1)).thenReturn(
        new SelectedCharity(
            1L,
            "مؤسسة مجدى يعقوب للقلب",
            0L,
            0L,
            "تعتمد مؤسسة مجدى يعقوب للقلب على التبرع والدعم المالي للقيام بمهمتها وانقاذ قلوب أطفال حيث اننا نطمح ونعمل على توسيع نطاق عملياتنا لتلبية الطلب الكبير والمتزايد على علاج القلب والأوعية الدموية للذين هم في أمس الحاجة إليه، وذلك إلى جانب تطوير العلاج والبحث واستثمار المواهب، بطريقة غير مسبوقة في المنطقة.",
            "email@email.com",
            "029876542",
            List.of(
                "برنامج القلب الأول",
                "برنامج خيري",
                "برنامج خيري يهدف إلى توفير العلاج للأطفال الذين يعانون من أمراض القلب والأوعية الدموية"
            )
        )
    );
    mvc
        .perform(RestDocumentationRequestBuilders.get("/api/v1/charities/{id}", 1)
            .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoib21hciIsImV4cCI6MTY3OTcyMTY0MSwiaWF0IjoxNjc5NzE4MDQxLCJzY29wZSI6InVzZXIuaW5mby5yZWFkIn0.0If6Qo5ZAMcq4TXnkEkNgpzHP60NEoTqUSV6Di1IgQ8eVR7Illu27ppvBBXGImw5_3oWXcT89XWH6sGEbyyg5wcxXBh_yLX-n4bi4xE5_MgSGZDqBfAGepZLczD7LzqbEpAC74Tajj05F2utI6TZkzviy8YDa60rE2IlWQoaR9pfwnhVJjsNnPW_T82cJyjT5hmVzZ7CScy5ZtSSL4QgslPy0IWixTRPna5GhA2GIMc_nfPuo7ePw6ggxXk1GSXliV5RPRp40EUpMBBxDWP362LSr5GSREWw2dF7QiPlfhH-uq46e5vQQZ8M28wjZYIOYpsW7ntaOBnNp9hx0suYNQ")
            .with(jwt().jwt((jwt) -> jwt.claim("scope", "USER"))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(
            """
                {
                  "id": 1,
                  "name": "مؤسسة مجدى يعقوب للقلب",
                  "currentPoints": 0,
                  "numberOfDonors": 0,
                  "about": "تعتمد مؤسسة مجدى يعقوب للقلب على التبرع والدعم المالي للقيام بمهمتها وانقاذ قلوب أطفال حيث اننا نطمح ونعمل على توسيع نطاق عملياتنا لتلبية الطلب الكبير والمتزايد على علاج القلب والأوعية الدموية للذين هم في أمس الحاجة إليه، وذلك إلى جانب تطوير العلاج والبحث واستثمار المواهب، بطريقة غير مسبوقة في المنطقة.",
                  "site": "email@email.com",
                  "phone": "029876542",
                  "programs": [
                    "برنامج القلب الأول",
                    "برنامج خيري",
                    "برنامج خيري يهدف إلى توفير العلاج للأطفال الذين يعانون من أمراض القلب والأوعية الدموية"
                  ]
                }
                """
        )).andDo(MockMvcRestDocumentation.document("specificCharity", HeaderDocumentation.requestHeaders(HeaderDocumentation.headerWithName("Authorization").description("JWT token"))));
  }

}