//package com.woowacourse.ternoko.api;
//
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.AUTHORIZATION;
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.BEARER_TYPE;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.AVAILABLE_DATE_TIME;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
//import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.NOW_MONTH_REQUEST;
//import static com.woowacourse.ternoko.support.fixture.InterviewFixture.INTERVIEW;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1_UPDATE_REQUEST;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.woowacourse.ternoko.api.restdocs.RestDocsTestSupporter;
//import com.woowacourse.ternoko.core.dto.response.CoachResponse;
//import com.woowacourse.ternoko.core.dto.response.ScheduleResponse;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//public class CoachControllerTest extends RestDocsTestSupporter {
//
//    @Test
//    @DisplayName("코치 - 면담 예약 내역 목록을 조회한다.")
//    void findAllInterviewByCoach() throws Exception {
//        // given
//        given(interviewService.findAllByCoach(any(), any(), any()))
//                .willReturn(ScheduleResponse.from(
//                        List.of(INTERVIEW)));
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/schedules")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1))
//                        .queryParam("year", String.valueOf(NOW_MONTH_REQUEST.getYear()))
//                        .queryParam("month", String.valueOf(NOW_MONTH_REQUEST.getMonth())))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("코치 - 면담 가능 시간을 저장한다.")
//    void saveCalendarTimes() throws Exception {
//        doNothing().when(coachService).putAvailableDateTimesByCoachId(any(), any());
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/calendar/times")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1))
//                        .content(objectMapper.writeValueAsString(MONTH_REQUEST)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("코치 - 면담 가능 시간 목록을 조회한다.")
//    void findCalendarTimes() throws Exception {
//        // given
//        given(coachService.findAvailableDateTimesByCoachId(any(), anyInt(), anyInt()))
//                .willReturn(List.of(AVAILABLE_DATE_TIME));
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/calendar/times")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1))
//                        .queryParam("coachId", String.valueOf(COACH1.getId()))
//                        .queryParam("year", String.valueOf(NOW_MONTH_REQUEST.getYear()))
//                        .queryParam("month", String.valueOf(NOW_MONTH_REQUEST.getMonth())))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("코치 - 내 정보를 조회한다.")
//    void findCoach() throws Exception {
//        // given
//        given(coachService.findCoach(any()))
//                .willReturn(CoachResponse.from(COACH1));
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/coaches/me")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("코치 - 내 정보를 수정한다.")
//    void updateCoach() throws Exception {
//        doNothing().when(coachService).partUpdateCrew(any(), any());
//        // given, when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .patch("/api/coaches/me")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1))
//                        .content(objectMapper.writeValueAsString(COACH1_UPDATE_REQUEST)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//}
