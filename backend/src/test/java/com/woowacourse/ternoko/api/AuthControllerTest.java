package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.ternoko.domain.MemberType;
import com.woowacourse.ternoko.dto.LoginResponse;
import com.woowacourse.ternoko.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest extends ControllerTest {

    @MockBean
    private AuthService authService;

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
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId())))
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
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId())))
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
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId())))
                        .queryParam("type", "all"))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
