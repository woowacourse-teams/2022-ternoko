package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH3;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.dto.CalendarRequest;
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
        final CalendarRequest calendarRequest = new CalendarRequest(List.of(
                LocalDateTime.of(2022, 7, 7, 14, 0),
                LocalDateTime.of(2022, 7, 7, 15, 0),
                LocalDateTime.of(2022, 7, 7, 16, 0)));

        // when
        final ExtractableResponse<Response> calendarResponse = put("/api/coaches/" + COACH3.getId() + "/calendar/times",
                calendarRequest);

        // then
        assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
