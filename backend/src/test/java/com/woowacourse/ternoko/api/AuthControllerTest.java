//package com.woowacourse.ternoko.api;
//
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.AUTHORIZATION;
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.BEARER_TYPE;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.woowacourse.ternoko.api.restdocs.RestDocsTestSupporter;
//import com.woowacourse.ternoko.auth.dto.response.LoginResponse;
//import com.woowacourse.ternoko.core.domain.member.MemberType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//public class AuthControllerTest extends RestDocsTestSupporter {
//
//    @Test
//    @DisplayName("Coach/Crew - 로그인을 한다.")
//    void login() throws Exception {
//        // given, when
//        when(authService.login(any(), any()))
//                .thenReturn(LoginResponse.of(MemberType.CREW, "sampleToken", false));
//
//        //then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/login")
//                        .queryParam("code", "slackCode")
//                        .queryParam("redirectUrl", "slackRedirectUrl"))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("Coach 요청 Type + 유효성 검사를 한다.")
//    void checkValidAccessTokenAndCoachType() throws Exception {
//        // given, when
//        doNothing().when(authService).checkMemberType(any(), any());
//
//        //then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/login/valid")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
//                        .queryParam("type", "COACH"))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("Crew 요청 Type + 유효성 검사를 한다.")
//    void checkValidAccessTokenAndCrewType() throws Exception {
//        // given, when
//        doNothing().when(authService).checkMemberType(any(), any());
//
//        //then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/login/valid")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
//                        .queryParam("type", "CREW"))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("All 요청 Type + 유효성 검사를 한다.")
//    void checkValidAccessTokenAndAllType() throws Exception {
//        // given, when
//        doNothing().when(authService).checkMemberType(any(), any());
//
//        //then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/login/valid")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
//                        .queryParam("type", "all"))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//}
