package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW2;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW2_UPDATE_REQUEST;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CrewControllerTest extends ControllerTest {

    @Test
    @DisplayName("크루 - 내 정보를 조회한다.")
    void findCoach() throws Exception {
        // given, when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/crews/me")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW2.getId()))))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치 - 내 정보를 수정한다.")
    void updateCoach() throws Exception {
        // given, when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/crews/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId())))
                        .content(objectMapper.writeValueAsString(CREW2_UPDATE_REQUEST)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
