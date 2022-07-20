package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.MemberFixture.AVAILABLE_DATE_TIME_REQUEST2;
import static com.woowacourse.ternoko.fixture.MemberFixture.AVAILABLE_DATE_TIME_REQUEST3;
import static com.woowacourse.ternoko.fixture.MemberFixture.AVAILABLE_DATE_TIME_REQUEST4;
import static com.woowacourse.ternoko.fixture.MemberFixture.AVAILABLE_TIMES;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH4;
import static com.woowacourse.ternoko.fixture.MemberFixture.TIME2;
import static com.woowacourse.ternoko.fixture.ReservationFixture.AFTER_TWO_DAYS;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.ternoko.dto.ScheduleResponse;
import com.woowacourse.ternoko.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CoachAcceptanceTest extends AcceptanceTest {

	@Test
	@DisplayName("코치 - 면담 예약 내역 목록을 조회한다.")
	void findAllByCoach() {

		final Header header = new Header(AUTHORIZATION,
			BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH4.getId())));
		// :todo 현재는 코치 값으로 accessToken 발급 중. 추후 크루 값으로 변경해야 함.
		// given
		createReservation(COACH4.getId(), header, "애쉬");
		createReservation(COACH4.getId(), header, "바니");
		createReservation(COACH4.getId(), header, "앤지");
		createReservation(COACH4.getId(), header, "열음");

		// when
		final ExtractableResponse<Response> response = RestAssured.given().log().all()
			.queryParam("year", AFTER_TWO_DAYS.getYear())
			.queryParam("month", AFTER_TWO_DAYS.getMonthValue())
			.header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH4.getId())))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/coaches/" + COACH4.getId() + "/schedules")
			.then().log().all()
			.extract();
		final ScheduleResponse scheduleResponse = response.body().as(ScheduleResponse.class);

		// then
		assertThat(scheduleResponse.getCalendar()).hasSize(4);
	}

	@Test
	@DisplayName("코치의 면담 가능 시간을 저장한다.")
	void saveCalendarTimes() {
		final Header header = new Header(AUTHORIZATION,
			BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH4.getId())));

		// given
		final AvailableDateTimeRequest AVAILABLEDATETIMEREQUEST = new AvailableDateTimeRequest(
			TIME2.getYear(),
			TIME2.getMonthValue(),
			AVAILABLE_TIMES);
		final AvailableDateTimesRequest availableDateTimesRequest = new AvailableDateTimesRequest(List.of(
			AVAILABLEDATETIMEREQUEST));

		// when
		final ExtractableResponse<Response> calendarResponse = put("/api/coaches/" + COACH4.getId() + "/calendar/times",
			header, availableDateTimesRequest);

		// then
		assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	@DisplayName("코치의 면담 가능 시간을 저장한다. - 여러 달")
	void saveCalendarsTimes() {
		final Header header = new Header(AUTHORIZATION,
			BEARER_TYPE + jwtProvider.createToken(String.valueOf(COACH4.getId())));
		// given
		final AvailableDateTimesRequest availableDateTimesRequest = new AvailableDateTimesRequest(List.of(
			AVAILABLE_DATE_TIME_REQUEST2,
			AVAILABLE_DATE_TIME_REQUEST3,
			AVAILABLE_DATE_TIME_REQUEST4));

		// when
		final ExtractableResponse<Response> calendarResponse = put("/api/coaches/" + COACH4.getId() + "/calendar/times",
			header, availableDateTimesRequest);

		// then
		assertThat(calendarResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
