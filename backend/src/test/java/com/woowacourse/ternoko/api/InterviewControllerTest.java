//package com.woowacourse.ternoko.api;
//
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.AUTHORIZATION;
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.BEARER_TYPE;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.AVAILABLE_DATE_TIME;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_MONTH_REQUEST;
//import static com.woowacourse.ternoko.support.fixture.InterviewFixture.COACH1_INTERVIEW_REQUEST1;
//import static com.woowacourse.ternoko.support.fixture.InterviewFixture.COACH1_INTERVIEW_REQUEST2;
//import static com.woowacourse.ternoko.support.fixture.InterviewFixture.INTERVIEW;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.woowacourse.ternoko.api.restdocs.RestDocsTestSupporter;
//import com.woowacourse.ternoko.core.dto.response.InterviewResponse;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//public class InterviewControllerTest extends RestDocsTestSupporter {
//
//    @BeforeEach
//    void setUp() throws Exception {
//        given(authService.isValid(any())).willReturn(true);
//    }
//
//    @Test
//    @DisplayName("크루 - 면담 예약을 생성한다.")
//    void createInterview() throws Exception {
//
//        given(interviewService.create(any(), any()))
//                .willReturn(1L);
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/interviews")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
//                        .content(objectMapper.writeValueAsString(COACH1_INTERVIEW_REQUEST1)))
//                .andExpect(status().isCreated())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("크루 - 면담 예약 내역을 조회한다.")
//    void findInterviewById() throws Exception {
//        given(interviewService.findAllByCrewId(any()))
//                .willReturn(List.of(InterviewResponse.from(INTERVIEW)));
//
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/interviews/{interviewId}", INTERVIEW.getId())
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("크루 - 면담 예약 내역 목록을 조회한다.")
//    void findAllInterviews() throws Exception {
//        given(interviewService.findAllByCrewId(any()))
//                .willReturn(List.of(InterviewResponse.from(INTERVIEW)));
//
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/interviews")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//
//
//    @Test
//    @DisplayName("크루 - 면담 수정시 면담 가능 시간 목록을 조회한다.")
//    void findCalendarTimesByInterviewId() throws Exception {
//        // given
//        given(interviewService.findAvailableDateTimesByCoachIdOrInterviewId(any(), any(), anyInt(), anyInt()))
//                .willReturn(List.of(AVAILABLE_DATE_TIME));
//
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/interviews/{interviewId}/calendar/times", INTERVIEW.getId())
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
//                        .queryParam("coachId", String.valueOf(COACH1.getId()))
//                        .queryParam("year", String.valueOf(NOW_MONTH_REQUEST.getYear()))
//                        .queryParam("month", String.valueOf(NOW_MONTH_REQUEST.getMonth())))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("크루 - 면담 예약 내역을 수정한다.")
//    void updateInterview() throws Exception {
//
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/interviews/{interviewId}", INTERVIEW.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1))
//                        .content(objectMapper.writeValueAsString(COACH1_INTERVIEW_REQUEST2)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("크루 - 면담 예약을 취소한다.")
//    void deleteInterview() throws Exception {
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/api/interviews/{interviewId}", INTERVIEW.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1)))
//                .andExpect(status().isNoContent())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("코치 - 면담 예약을 취소한다.")
//    void cancelInterview() throws Exception {
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .patch("/api/interviews/{interviewId}", INTERVIEW.getId())
//                        .queryParam("onlyInterview", "true")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
//                .andExpect(status().isNoContent())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("코치 - 면담 예약을 취소 + 되는 시간을 삭제한다.")
//    void cancelInterviewWithDeleteAvailableDateTime() throws Exception {
//
//        //when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .patch("/api/interviews/{interviewId}", INTERVIEW.getId())
//                        .queryParam("onlyInterview", "false")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
//                .andExpect(status().isNoContent())
//                .andDo(restDocs.document());
//    }
//}
