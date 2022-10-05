package com.woowacourse.ternoko.support.fixture.refactor;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.네오;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.브리;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.준;
import static com.woowacourse.ternoko.support.fixture.refactor.MemberFixture.토미;

import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeSummaryRequest;
import com.woowacourse.ternoko.core.dto.request.CalendarRequest;
import java.time.LocalDateTime;
import java.util.List;

public class AvailableDateTimeFixture {
    public static final LocalDateTime _2022_07_01_10_00 = LocalDateTime.of(2022, 7, 1, 10, 0);
    public static final LocalDateTime _2022_07_01_11_00 = LocalDateTime.of(2022, 7, 1, 11, 0);
    public static final LocalDateTime _2022_07_01_12_00 = LocalDateTime.of(2022, 7, 1, 12, 0);
    public static final LocalDateTime _2022_07_02_10_00 = LocalDateTime.of(2022, 7, 2, 10, 0);
    public static final LocalDateTime _2022_07_02_11_00 = LocalDateTime.of(2022, 7, 2, 11, 0);
    public static final LocalDateTime _2022_07_02_12_00 = LocalDateTime.of(2022, 7, 2, 12, 0);
    public static final LocalDateTime _2022_07_03_10_00 = LocalDateTime.of(2022, 7, 3, 10, 0);
    public static final LocalDateTime _2022_07_03_11_00 = LocalDateTime.of(2022, 7, 3, 11, 0);
    public static final LocalDateTime _2022_07_03_12_00 = LocalDateTime.of(2022, 7, 3, 12, 0);

    public static final AvailableDateTimeSummaryRequest REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_01_10_00 = createAvailableDateTimeSummaryRequest(
            _2022_07_01_10_00, OPEN);
    public static final AvailableDateTimeSummaryRequest REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_01_11_00 = createAvailableDateTimeSummaryRequest(
            _2022_07_01_11_00, OPEN);
    public static final AvailableDateTimeSummaryRequest REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_01_12_00 = createAvailableDateTimeSummaryRequest(
            _2022_07_01_12_00, OPEN);

    public static final AvailableDateTimeSummaryRequest REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_02_10_00 = createAvailableDateTimeSummaryRequest(
            _2022_07_02_10_00, OPEN);
    public static final AvailableDateTimeSummaryRequest REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_02_11_00 = createAvailableDateTimeSummaryRequest(
            _2022_07_02_11_00, OPEN);
    public static final AvailableDateTimeSummaryRequest REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_02_12_00 = createAvailableDateTimeSummaryRequest(
            _2022_07_02_12_00, OPEN);

    public static final AvailableDateTimeSummaryRequest REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_03_10_00 = createAvailableDateTimeSummaryRequest(
            _2022_07_03_10_00, OPEN);
    public static final AvailableDateTimeSummaryRequest REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_03_11_00 = createAvailableDateTimeSummaryRequest(
            _2022_07_03_11_00, OPEN);
    public static final AvailableDateTimeSummaryRequest REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_03_12_00 = createAvailableDateTimeSummaryRequest(
            _2022_07_03_12_00, OPEN);

    public static final AvailableDateTime 면담가능시간_준_2022_07_01_10_00 = createAvailableDateTime(1L, 준,
            _2022_07_01_10_00);
    public static final AvailableDateTime 면담가능시간_준_2022_07_01_11_00 = createAvailableDateTime(2L, 준,
            _2022_07_01_11_00);
    public static final AvailableDateTime 면담가능시간_준_2022_07_01_12_00 = createAvailableDateTime(3L, 준,
            _2022_07_01_12_00);

    public static final AvailableDateTime 면담가능시간_토미_2022_07_01_10_00 = createAvailableDateTime(4L, 토미,
            _2022_07_01_10_00);
    public static final AvailableDateTime 면담가능시간_토미_2022_07_01_11_00 = createAvailableDateTime(5L, 토미,
            _2022_07_01_11_00);
    public static final AvailableDateTime 면담가능시간_토미_2022_07_01_12_00 = createAvailableDateTime(6L, 토미,
            _2022_07_01_12_00);

    public static final AvailableDateTime 면담가능시간_브리_2022_07_01_10_00 = createAvailableDateTime(7L, 브리,
            _2022_07_01_10_00);
    public static final AvailableDateTime 면담가능시간_브리_2022_07_01_11_00 = createAvailableDateTime(8L, 브리,
            _2022_07_01_11_00);
    public static final AvailableDateTime 면담가능시간_브리_2022_07_01_12_00 = createAvailableDateTime(9L, 브리,
            _2022_07_01_12_00);

    public static final AvailableDateTime 면담가능시간_네오_2022_07_01_10_00 = createAvailableDateTime(10L, 네오,
            _2022_07_01_10_00);
    public static final AvailableDateTime 면담가능시간_네오_2022_07_01_11_00 = createAvailableDateTime(11L, 네오,
            _2022_07_01_11_00);
    public static final AvailableDateTime 면담가능시간_네오_2022_07_01_12_00 = createAvailableDateTime(12L, 네오,
            _2022_07_01_12_00);

    public static final AvailableDateTimeRequest REQUEST_OPEN_DATE_TIME_2022_7_1_10_TO_12 = createAvailableDateTimeRequest(
            REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_01_10_00,
            REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_01_11_00,
            REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_01_12_00);
    public static final AvailableDateTimeRequest REQUEST_OPEN_DATE_TIME_2022_7_2_10_TO_12 = createAvailableDateTimeRequest(
            REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_02_10_00,
            REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_02_11_00,
            REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_02_12_00);
    public static final AvailableDateTimeRequest REQUEST_OPEN_DATE_TIME_2022_7_3_10_TO_12 = createAvailableDateTimeRequest(
            REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_03_10_00,
            REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_03_11_00,
            REQUEST_SUMMARY_OPEN_DATE_TIME_2022_7_03_12_00);

    public static final CalendarRequest 면담가능시간생성요청정보_2022_07_01_10_TO_12 = createCalendarRequest(REQUEST_OPEN_DATE_TIME_2022_7_1_10_TO_12);
    public static final CalendarRequest 면담가능시간생성요청정보_2022_07_02_10_TO_12 = createCalendarRequest(REQUEST_OPEN_DATE_TIME_2022_7_2_10_TO_12);
    public static final CalendarRequest 면담가능시간생성요청정보_2022_07_03_10_TO_12 = createCalendarRequest(REQUEST_OPEN_DATE_TIME_2022_7_3_10_TO_12);

    private static CalendarRequest createCalendarRequest(final AvailableDateTimeRequest availableDateTimeRequest) {
        return new CalendarRequest(List.of(availableDateTimeRequest));
    }

    private static AvailableDateTime createAvailableDateTime(final Long id,
                                                             final Coach coach,
                                                             final LocalDateTime dateTime) {
        return new AvailableDateTime(id, coach.getId(), dateTime, OPEN);
    }

    // 같은 년, 월의 AvailableDateTimeSummaryRequest만 넣어야 함.
    private static AvailableDateTimeRequest createAvailableDateTimeRequest(final AvailableDateTimeSummaryRequest...availableDateTimeSummaryRequests) {
        final AvailableDateTimeSummaryRequest request = availableDateTimeSummaryRequests[0];
        final LocalDateTime dateTime = request.getTime();
        final int year = dateTime.getYear();
        final int monthValue = dateTime.getMonthValue();
        return new AvailableDateTimeRequest(year, monthValue, List.of(availableDateTimeSummaryRequests));
    }

    //    public static final CalendarRequest REQUEST_2022_7_1_1011_12
    private static AvailableDateTimeSummaryRequest createAvailableDateTimeSummaryRequest(final LocalDateTime dateTime,
                                                                                         final AvailableDateTimeStatus availableDateTimeStatus) {
        return new AvailableDateTimeSummaryRequest(dateTime, availableDateTimeStatus);
    }

//    private static LocalDateTime createDateTime(final LocalDateTime localDateTime) {
//        TimeMachine.timeTravelAt(localDateTime);
//        final LocalDateTime created = TimeMachine.dateTimeOfNow();
//        TimeMachine.reset();
//        return created;
//    }
}
