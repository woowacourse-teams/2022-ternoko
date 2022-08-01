package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CoachControllerTest extends ControllerTest {

    @Test
    @DisplayName("코치 - 면담 예약 내역 목록을 조회한다.")
    void findAllByCoach() throws Exception {
        // given
        createCalendarTimes(COACH1.getId());
        createReservations(CREW1.getId());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/coaches/" + COACH1.getId() + "/schedules")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId())))
                        .queryParam("year", "2030")
                        .queryParam("month", "8"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("calendar[]").type(JsonFieldType.ARRAY).description("코치 면담 예약 리스트"),
                                fieldWithPath("calendar[].id").type(JsonFieldType.NUMBER).description("면담 예약 아이디"),
                                fieldWithPath("calendar[].crewNickname").type(JsonFieldType.STRING)
                                        .description("면담 예약한 크루 닉네임"),
                                fieldWithPath("calendar[].interviewStartTime").type(JsonFieldType.STRING)
                                        .description("면담 시작 시간"),
                                fieldWithPath("calendar[].interviewEndTime").type(JsonFieldType.STRING)
                                        .description("면담 종료 시간")
                        )));
    }
}
