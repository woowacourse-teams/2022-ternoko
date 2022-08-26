package com.woowacourse.ternoko.api;

import static com.woowacourse.support.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.support.fixture.CoachAvailableTimeFixture.NOW_MONTH_REQUEST;
import static com.woowacourse.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.support.fixture.MemberFixture.COACH1_UPDATE_REQUEST;
import static com.woowacourse.support.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.BEARER_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CoachControllerTest extends ControllerTest {

    @Test
    @DisplayName("코치 - 면담 예약 내역 목록을 조회한다.")
    void findAllInterviewByCoach() throws Exception {
        // given
        createCalendarTimes(COACH1);
        createInterviews(CREW1);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/schedules")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1))
                        .queryParam("year", String.valueOf(NOW_MONTH_REQUEST.getYear()))
                        .queryParam("month", String.valueOf(NOW_MONTH_REQUEST.getMonth())))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치 - 면담 가능 시간을 저장한다.")
    void saveCalendarTimes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/calendar/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1))
                        .content(objectMapper.writeValueAsString(MONTH_REQUEST)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치 - 면담 가능 시간 목록을 조회한다.")
    void findCalendarTimes() throws Exception {
        // given
        createCalendarTimes(COACH1);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/calendar/times")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1))
                        .queryParam("coachId", String.valueOf(COACH1.getId()))
                        .queryParam("year", String.valueOf(NOW_MONTH_REQUEST.getYear()))
                        .queryParam("month", String.valueOf(NOW_MONTH_REQUEST.getMonth())))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루 - 면담 수정시 면담 가능 시간 목록을 조회한다.")
    void findCalendarTimesByInterviewId() throws Exception {
        // given
        createCalendarTimes(COACH1);
        final Long interviewId = createInterview(CREW1);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/interviews/{interviewId}/calendar/times", interviewId)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
                        .queryParam("coachId", String.valueOf(COACH1.getId()))
                        .queryParam("year", String.valueOf(NOW_MONTH_REQUEST.getYear()))
                        .queryParam("month", String.valueOf(NOW_MONTH_REQUEST.getMonth())))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치 - 내 정보를 조회한다.")
    void findCoach() throws Exception {
        // given, when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/coaches/me")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치 - 내 정보를 수정한다.")
    void updateCoach() throws Exception {
        // given, when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/coaches/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1))
                        .content(objectMapper.writeValueAsString(COACH1_UPDATE_REQUEST)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
