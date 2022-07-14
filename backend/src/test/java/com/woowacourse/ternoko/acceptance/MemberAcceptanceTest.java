package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.MemberFixture.AVAILABLE_TIMES;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.dto.CalendarRequest;
import com.woowacourse.ternoko.dto.CalendarResponse;
import com.woowacourse.ternoko.dto.CoachesResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void find() {
        // given, when
        final ExtractableResponse<Response> response = get("/api/reservations/coaches");

        //then
        final CoachesResponse coachesResponse = response.body().as(CoachesResponse.class);
        assertThat(coachesResponse.getCoaches()).hasSize(4);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 저장한다.")
    void saveCalendarTimes() {
        // given
        final CalendarRequest calendarRequest = new CalendarRequest(AVAILABLE_TIMES);

        // when
        final ExtractableResponse<Response> calendarResponse = put("/api/coaches/" + COACH3.getId() + "/calendar/times",
                calendarRequest);

        // then
        assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 조회한다.")
    void findCalendarTimes() {
        // given
        final List<LocalDateTime> times = AVAILABLE_TIMES;

        final CalendarRequest calendarRequest = new CalendarRequest(times);
        put("/api/coaches/" + COACH3.getId() + "/calendar/times", calendarRequest);

        final ExtractableResponse<Response> calendarResponse = get(
                "/api/coaches/" + COACH3.getId() + "/calendar/times");

        // when
        final CalendarResponse response = calendarResponse.body().as(CalendarResponse.class);

        // then
        assertThat(response.getCalendarTimes())
                .hasSize(3)
                .containsAnyElementsOf(times);
    }
}
