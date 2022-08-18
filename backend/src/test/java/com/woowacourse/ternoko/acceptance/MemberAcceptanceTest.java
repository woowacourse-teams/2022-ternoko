package com.woowacourse.ternoko.acceptance;

import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.FIRST_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.MONTH_REQUEST;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_2_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.NOW_PLUS_3_DAYS;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.SECOND_TIME;
import static com.woowacourse.ternoko.fixture.CoachAvailableTimeFixture.THIRD_TIME;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH3;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimeRequest;
import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimeResponse;
import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimeSummaryRequest;
import com.woowacourse.ternoko.availabledatetime.dto.AvailableDateTimesResponse;
import com.woowacourse.ternoko.dto.CalendarRequest;
import com.woowacourse.ternoko.dto.CoachesResponse;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void find() {
        // given & when
        final ExtractableResponse<Response> response = get("/api/coaches", generateHeader(CREW1));

        //then
        final CoachesResponse coachesResponse = response.body().as(CoachesResponse.class);
        assertThat(coachesResponse.getCoaches()).hasSize(4);
    }

    @Test
    @DisplayName("코치의 면담 가능 시간을 조회한다.")
    void findCalendarTimes() {
        // given
        put("/api/calendar/times", generateHeader(COACH3), MONTH_REQUEST);

        final Header crewHeader = generateHeader(CREW1);
        final ExtractableResponse<Response> calendarResponse = get(
                "/api/calendar/times?"
                        + "coachId=" + COACH3.getId()
                        + "&year=" + NOW.getYear()
                        + "&month=" + NOW.getMonthValue(),
                crewHeader);

        // when
        final AvailableDateTimesResponse response = calendarResponse.body().as(AvailableDateTimesResponse.class);

        // then
        assertThat(response.getCalendarTimes())
                .hasSize(9);
    }

    @Test
    @DisplayName("크루가 자신이 신청한 interview를 수정할 때 코치의 면담 가능 시간을 조회한다. - 해당 인터뷰 시작시간만 USED로 반환되어야한다.")
    void findCalendarTimesByCrew() {
        // given
        putAvailableTimes();
        final ExtractableResponse<Response> interviewResponse = createInterview(CREW1, COACH3.getId(),
                LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME));
        final Long interviewId = parseLocationHeader(interviewResponse, "/api/interviews/");
        createInterview(CREW1, COACH3.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME));

        // when
        final Header crewHeader = generateHeader(CREW1);
        final ExtractableResponse<Response> calendarResponse = get(
                "/api/interviews/" + interviewId + "/calendar/times?"
                        + "coachId=" + COACH3.getId()
                        + "&year=" + NOW.getYear()
                        + "&month=" + NOW.getMonthValue(),
                crewHeader);
        final AvailableDateTimesResponse response = calendarResponse.body().as(AvailableDateTimesResponse.class);

        // then
        final List<AvailableDateTimeResponse> actual = response.getCalendarTimes();
        assertThat(actual).hasSize(3)
                .containsExactly(new AvailableDateTimeResponse(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME), USED),
                        new AvailableDateTimeResponse(LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME), OPEN),
                        new AvailableDateTimeResponse(LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME), OPEN));
    }

    private void putAvailableTimes() {
        final List<AvailableDateTimeSummaryRequest> inputTimes = List.of(
                new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME), OPEN),
                new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME), OPEN),
                new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME), OPEN),
                new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME), OPEN));
        final AvailableDateTimeRequest afterTwoDays = new AvailableDateTimeRequest(NOW.getYear(), NOW.getMonthValue(),
                inputTimes);
        put("/api/calendar/times", generateHeader(COACH3), new CalendarRequest(List.of(afterTwoDays)));
    }
}
