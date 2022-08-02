package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class MemberControllerTest extends RestDocsTestSupport {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void findCoaches() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/coaches")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId()))))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("닉네임 중복 검사를 한다. ")
    void checkUniqueNickname() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/login/check")
                        .queryParam("nickname", COACH3.getNickname()))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다.")
    void saveCalendarTimes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/calendar/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId())))
                        .content(objectMapper.writeValueAsString(MONTH_REQUEST)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치의 면담 가능 시간 목록을 조회한다.")
    void findCalendarTimes() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/calendar/times")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId())))
                        .queryParam("coachId", String.valueOf(COACH1.getId()))
                        .queryParam("year", String.valueOf(NOW_MONTH_REQUEST.getYear()))
                        .queryParam("month", String.valueOf(NOW_MONTH_REQUEST.getMonth())))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    private void createCalendarTimes(Long coachId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/calendar/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(coachId)))
                        .content(objectMapper.writeValueAsString(MONTH_REQUEST)))
                .andExpect(status().isOk());
    }
}
