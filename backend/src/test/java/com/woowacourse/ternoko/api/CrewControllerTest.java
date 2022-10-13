//package com.woowacourse.ternoko.api;
//
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.AUTHORIZATION;
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.BEARER_TYPE;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW2;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW2_UPDATE_REQUEST;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.woowacourse.ternoko.api.restdocs.RestDocsTestSupporter;
//import com.woowacourse.ternoko.core.dto.response.CrewResponse;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//public class CrewControllerTest extends RestDocsTestSupporter {
//
//    @Test
//    @DisplayName("크루 - 내 정보를 조회한다.")
//    void findCrew() throws Exception {
//        given(crewService.findCrew(any()))
//                .willReturn(CrewResponse.from(CREW2));
//        // given, when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/crews/me")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("크루 - 내 정보를 수정한다.")
//    void updateCrew() throws Exception {
//        doNothing().when(crewService).partUpdateCrew(any(), any());
//        // given, when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .patch("/api/crews/me")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2))
//                        .content(objectMapper.writeValueAsString(CREW2_UPDATE_REQUEST)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//}
