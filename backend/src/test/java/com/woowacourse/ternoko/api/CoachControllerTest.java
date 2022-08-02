package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
                        .get("/api/schedules")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH1.getId())))
                        .queryParam("year", String.valueOf(NOW_MONTH_REQUEST.getYear()))
                        .queryParam("month", String.valueOf(NOW_MONTH_REQUEST.getMonth())))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
