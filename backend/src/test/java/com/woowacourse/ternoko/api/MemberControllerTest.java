package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
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
                        .queryParam("nickname", "토미"))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다.")
    void saveCalendarTimes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/calendar/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId())))
                        .characterEncoding("utf-8")
                        .content(readJson("json/members/save-calendar-times.json")))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(fieldWithPath("calendarTimes.[].year").type(JsonFieldType.NUMBER)
                                        .description("년도"),
                                fieldWithPath("calendarTimes.[].month").type(JsonFieldType.NUMBER)
                                        .description("달"),
                                fieldWithPath("calendarTimes.[].times").type(JsonFieldType.ARRAY)
                                        .description("면담 가능 시간")
                        )));
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
                .andDo(restDocs.document(
                        responseFields(fieldWithPath("calendarTimes[]").type(JsonFieldType.ARRAY)
                                .description("면담 가능 시간 목록")
                        )));
    }

    private void createCalendarTimes(Long coachId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/calendar/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(coachId)))
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(MONTH_REQUEST)))
                .andExpect(status().isOk());
    }
}
