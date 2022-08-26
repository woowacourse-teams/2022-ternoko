package com.woowacourse.ternoko.api;

import static com.woowacourse.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.support.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.BEARER_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("크루 - 코치 목록을 조회한다.")
    void findCoaches() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/coaches")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("[코치/크루] - 닉네임 중복 검사를 한다. ")
    void checkUniqueNickname() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/login/check")
                        .queryParam("nickname", COACH3.getNickname()))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
