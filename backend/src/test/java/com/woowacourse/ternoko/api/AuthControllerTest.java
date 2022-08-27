package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.ternoko.controller.AuthController;
import com.woowacourse.ternoko.domain.member.MemberType;
import com.woowacourse.ternoko.login.domain.dto.LoginResponse;
import com.woowacourse.ternoko.support.utils.WebMVCTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest extends WebMVCTest {

    @Test
    @DisplayName("Coach/Crew - 로그인을 한다.")
    void login() throws Exception {
        // given, when
        when(authService.login(any(), any()))
                .thenReturn(LoginResponse.of(MemberType.CREW, "sampleToken", false));

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/login")
                        .queryParam("code", "slackCode")
                        .queryParam("redirectUrl", "slackRedirectUrl"))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("Coach 요청 Type + 유효성 검사를 한다.")
    void checkValidAccessTokenAndCoachType() throws Exception {
        // given, when
        doNothing().when(authService).checkMemberType(any(), any());

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/login/valid")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
                        .queryParam("type", "COACH"))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("Crew 요청 Type + 유효성 검사를 한다.")
    void checkValidAccessTokenAndCrewType() throws Exception {
        // given, when
        doNothing().when(authService).checkMemberType(any(), any());

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/login/valid")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
                        .queryParam("type", "CREW"))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("All 요청 Type + 유효성 검사를 한다.")
    void checkValidAccessTokenAndAllType() throws Exception {
        // given, when
        doNothing().when(authService).checkMemberType(any(), any());

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/login/valid")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
                        .queryParam("type", "all"))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
