package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTHS_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_3_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_4_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.ReservationFixture.FORM_ITEM_REQUESTS;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.dto.AvailableDateTimesResponse;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("코치의 면담 가능 시간을 조회한다.")
    void findCalendarTimes() {
        // given
        put("/api/coaches/" + COACH3.getId() + "/calendar/times", MONTHS_REQUEST);
        final ExtractableResponse<Response> calendarResponse = get("/api/coaches/" + COACH3.getId()
                + "/calendar/times?year=" + NOW_PLUS_2_DAYS.getYear()
                + "&month=" + NOW_PLUS_2_DAYS.getMonthValue());

        // when
        final AvailableDateTimesResponse response = calendarResponse.body().as(AvailableDateTimesResponse.class);

        // then
        assertThat(response.getCalendarTimes())
                .hasSize(9)
                .containsExactly(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                        LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME),
                        LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
                        LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        LocalDateTime.of(NOW_PLUS_3_DAYS, THIRD_TIME),
                        LocalDateTime.of(NOW_PLUS_4_DAYS, FIRST_TIME),
                        LocalDateTime.of(NOW_PLUS_4_DAYS, SECOND_TIME),
                        LocalDateTime.of(NOW_PLUS_4_DAYS, THIRD_TIME));

    }

    @Test
    @DisplayName("코치와 면담을 예약하면 해당 시간은 코치의 면담 가능 시간 목록에서 삭제된다.")
    void findCalendarTimes_WhenCreateReservation_ThenExcludeDateTime() {
        // given
        put("/api/coaches/" + COACH3.getId() + "/calendar/times", MONTHS_REQUEST);

        final ReservationRequest reservationRequest = new ReservationRequest("바니",
                LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                FORM_ITEM_REQUESTS);
        post("/api/reservations/coaches/" + COACH3.getId(), reservationRequest);

        final ExtractableResponse<Response> calendarResponse = get("/api/coaches/" + COACH3.getId()
                + "/calendar/times?year=" + NOW_PLUS_2_DAYS.getYear()
                + "&month=" + NOW_PLUS_2_DAYS.getMonthValue());

        // when
        final AvailableDateTimesResponse response = calendarResponse.body().as(AvailableDateTimesResponse.class);

        // then
        assertThat(response.getCalendarTimes())
                .hasSize(8)
                .containsExactly(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                        LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME),
                        LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
                        LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                        LocalDateTime.of(NOW_PLUS_3_DAYS, THIRD_TIME),
                        LocalDateTime.of(NOW_PLUS_4_DAYS, FIRST_TIME),
                        LocalDateTime.of(NOW_PLUS_4_DAYS, SECOND_TIME),
                        LocalDateTime.of(NOW_PLUS_4_DAYS, THIRD_TIME));

    }
}
