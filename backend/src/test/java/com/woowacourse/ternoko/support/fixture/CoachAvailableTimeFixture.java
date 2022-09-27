package com.woowacourse.ternoko.support.fixture;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;

import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus;
import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeSummaryRequest;
import com.woowacourse.ternoko.core.dto.request.CalendarRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public class CoachAvailableTimeFixture {

    public static final LocalDate NOW = LocalDate.now()
            .plusDays(4).getMonthValue() > LocalDate.now().getMonthValue()
            ? LocalDate.now().plusDays(4) : LocalDate.now();

    public static final LocalDate NOW_MINUS_2_DAYS = NOW.minusDays(2);
    public static final LocalDate NOW_PLUS_2_DAYS = NOW.plusDays(2);
    public static final LocalDate NOW_PLUS_3_DAYS = NOW.plusDays(3);
    public static final LocalDate NOW_PLUS_4_DAYS = NOW.plusDays(4);

    public static final LocalDate NOW_PLUS_1_MONTH = NOW.plusMonths(1);

    public static final LocalTime FIRST_TIME = LocalTime.of(11, 0);
    public static final LocalTime SECOND_TIME = LocalTime.of(14, 0);
    public static final LocalTime THIRD_TIME = LocalTime.of(16, 0);

    public static final AvailableDateTime AVAILABLE_DATE_TIME = new AvailableDateTime(COACH1.getId(),
            LocalDateTime.now(),
            AvailableDateTimeStatus.OPEN);

    public static final AvailableDateTime AVAILABLE_DATE_TIME_COACH1_1 = createAvailableDateTime(1L, 10);
    public static final AvailableDateTime AVAILABLE_DATE_TIME_COACH1_2 = createAvailableDateTime(2L, 11);
    public static final AvailableDateTime AVAILABLE_DATE_TIME_COACH1_3 = createAvailableDateTime(3L, 12);
    public static final AvailableDateTime AVAILABLE_DATE_TIME_COACH1_4 = createAvailableDateTime(4L, 13);

    private static AvailableDateTime createAvailableDateTime(final Long id, final int hour) {
        return new AvailableDateTime(id, COACH1.getId(), LocalDateTime.of(NOW_PLUS_2_DAYS, LocalTime.of(hour, 0)), OPEN);
    }

    public static final AvailableDateTimeRequest PAST_TIME_REQUEST = new AvailableDateTimeRequest(
            LocalDate.now().getYear(),
            LocalDate.now().getMonthValue(),
            List.of(new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now().minusDays(2), FIRST_TIME),
                            OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now().minusDays(2), SECOND_TIME),
                            OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now().minusDays(2), THIRD_TIME),
                            OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now(), FIRST_TIME), OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now(), SECOND_TIME), OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now(), THIRD_TIME), OPEN)));

    public static final AvailableDateTimeRequest PAST_TIME_RESPONSE = new AvailableDateTimeRequest(
            LocalDate.now().getYear(),
            LocalDate.now().getMonthValue(),
            List.of(new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now().minusDays(2), FIRST_TIME),
                            OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now().minusDays(2), SECOND_TIME),
                            OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now().minusDays(2), THIRD_TIME),
                            OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now(), FIRST_TIME), OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now(), SECOND_TIME), OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(LocalDate.now(), THIRD_TIME), OPEN)));

    public static final AvailableDateTimeRequest NOW_MONTH_REQUEST;

    public static final LocalDateTime NOW_PLUS_2_DAYS_FIRST_TIME = LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME);
    public static final LocalDateTime NOW_PLUS_2_DAYS_SECOND_TIME = LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME);

    static {
        NOW_MONTH_REQUEST = new AvailableDateTimeRequest(
                NOW.getYear(),
                NOW.getMonthValue(),
                List.of(new AvailableDateTimeSummaryRequest(NOW_PLUS_2_DAYS_FIRST_TIME, OPEN),
                        new AvailableDateTimeSummaryRequest(NOW_PLUS_2_DAYS_SECOND_TIME, OPEN),
                        new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME), OPEN),
                        new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME), OPEN),
                        new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME), OPEN),
                        new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_3_DAYS, THIRD_TIME), OPEN),
                        new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_4_DAYS, FIRST_TIME), OPEN),
                        new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_4_DAYS, SECOND_TIME), OPEN),
                        new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_4_DAYS, THIRD_TIME), OPEN)));
    }

    public static final AvailableDateTimeRequest NEXT_MONTH_REQUEST = new AvailableDateTimeRequest(
            NOW_PLUS_1_MONTH.getYear(),
            NOW_PLUS_1_MONTH.getMonthValue(),
            List.of(new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_1_MONTH, FIRST_TIME), OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME), OPEN),
                    new AvailableDateTimeSummaryRequest(LocalDateTime.of(NOW_PLUS_1_MONTH, THIRD_TIME), OPEN)));

    public static final CalendarRequest PAST_REQUEST = new CalendarRequest(
            List.of(PAST_TIME_RESPONSE));

    public static final CalendarRequest MONTH_REQUEST = new CalendarRequest(
            List.of(NOW_MONTH_REQUEST));

    public static final CalendarRequest MONTHS_REQUEST = new CalendarRequest(
            List.of(NOW_MONTH_REQUEST, NEXT_MONTH_REQUEST));
}
