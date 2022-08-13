package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.InterviewFixture.COACH1_INTERVIEW_REQUEST1;
import static com.woowacourse.ternoko.fixture.InterviewFixture.COACH1_INTERVIEW_REQUEST2;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class InterviewControllerTest extends ControllerTest {

    @Test
    @DisplayName("크루 - 면담 예약을 생성한다.")
    void createInterview() throws Exception {
        //given
        createCalendarTimes(COACH1.getId());

        //when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId())))
                        .content(objectMapper.writeValueAsString(COACH1_INTERVIEW_REQUEST1)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루 - 면담 예약 내역을 조회한다.")
    void findInterviewById() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        final Long interviewId = createInterview(CREW1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/interviews/{interviewId}", interviewId)
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId()))))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루 - 면담 예약 내역 목록을 조회한다.")
    void findAllInterviews() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        createInterviews(CREW1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/interviews")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId()))))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루 - 면담 예약 내역을 수정한다.")
    void updateInterview() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        final Long interviewId = createInterview(CREW1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/interviews/{interviewId}", interviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId())))
                        .content(objectMapper.writeValueAsString(COACH1_INTERVIEW_REQUEST2)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루 - 면담 예약을 취소한다.")
    void deleteInterview() throws Exception {
        //given
        createCalendarTimes(COACH1.getId());
        final Long interviewId = createInterview(CREW1.getId());

        //when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/interviews/{interviewId}", interviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId()))))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치 - 면담 예약을 취소한다.")
    void cancelInterview() throws Exception {
        //given
        createCalendarTimes(COACH1.getId());
        final Long interviewId = createInterview(CREW1.getId());

        //when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/interviews/{interviewId}", interviewId)
                        .queryParam("onlyInterview", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId()))))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치 - 면담 예약을 취소 + 되는 시간을 삭제한다.")
    void cancelInterviewWithDeleteAvailableDateTime() throws Exception {
        //given
        createCalendarTimes(COACH1.getId());
        final Long interviewId = createInterview(CREW1.getId());

        //when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/interviews/{interviewId}", interviewId)
                        .queryParam("onlyInterview", "false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId()))))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document());
    }
}
