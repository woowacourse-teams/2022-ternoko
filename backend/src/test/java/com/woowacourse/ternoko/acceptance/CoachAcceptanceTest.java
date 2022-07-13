package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.fixture.ReservationFixture.COACH4;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.dto.ScheduleResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class CoachAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("코치 - 면담 예약 내역 목록을 조회한다.")
    void findAllByCoach() {
        // given
        createReservation(COACH4.getId(), "애쉬");
        createReservation(COACH4.getId(), "바니");
        createReservation(COACH4.getId(), "앤지");
        createReservation(COACH4.getId(), "열음");

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("year", 2022)
                .queryParam("month", 7)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/coaches/" + COACH4.getId() + "/schedules")
                .then().log().all()
                .extract();
        final ScheduleResponse scheduleResponse = response.body().as(ScheduleResponse.class);

        // then
        assertThat(scheduleResponse.getCalendar()).hasSize(4);
    }
}
