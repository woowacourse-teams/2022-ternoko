package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.config.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.config.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.MemberFixture.TIME2;
import static com.woowacourse.ternoko.fixture.MemberFixture.TIME3;
import static com.woowacourse.ternoko.fixture.MemberFixture.TIME4;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.ternoko.dto.AvailableDateTimesResponse;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class MemberAcceptanceTest extends AcceptanceTest {

	@Test
	@DisplayName("코치 목록을 조회한다.")
	void find() {
		//given
		final Header header = new Header(AUTHORIZATION,
			BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId())));

		//when
		final ExtractableResponse<Response> response = get("/api/reservations/coaches", header);

		//then
		final CoachesResponse coachesResponse = response.body().as(CoachesResponse.class);
		assertThat(coachesResponse.getCoaches()).hasSize(4);
	}

	@Test
	@DisplayName("코치의 면담 가능 시간을 조회한다.")
	void findCalendarTimes() {
		final Header header = new Header(AUTHORIZATION,
			BEARER_TYPE + jwtProvider.createToken(String.valueOf(CREW1.getId())));

		// given
		//TODO: Fixture 리팩토링 후 수정
		put("/api/coaches/" + COACH3.getId() + "/calendar/times", header, MONTH_REQUEST);

		final ExtractableResponse<Response> calendarResponse = get(
			"/api/coaches/" + COACH3.getId() + "/calendar/times?year="+ NOW.getYear()+"&month="+NOW.getMonthValue(), header);

		// when
		final AvailableDateTimesResponse response = calendarResponse.body().as(AvailableDateTimesResponse.class);

		// then
		assertThat(response.getCalendarTimes())
			.hasSize(9);
	}
}
