package com.woowacourse.ternoko.fixture;

import com.woowacourse.ternoko.dto.request.AvailableDateTimeRequest;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CoachAvailableTimeFixture {

    public static final LocalDate NOW = LocalDate.
            now().plusDays(4).getMonthValue() > LocalDate.now().getMonthValue() ?
            LocalDate.now().plusDays(4) : LocalDate.now();
    public static final LocalDate NOW_PLUS_2_DAYS = NOW.plusDays(2);
    public static final LocalDate NOW_PLUS_3_DAYS = NOW.plusDays(3);
    public static final LocalDate NOW_PLUS_4_DAYS = NOW.plusDays(4);

    public static final LocalDate NOW_PLUS_1_MONTH = NOW.plusMonths(1);

    public static final LocalTime FIRST_TIME = LocalTime.of(11, 0);
    public static final LocalTime SECOND_TIME = LocalTime.of(14, 0);
    public static final LocalTime THIRD_TIME = LocalTime.of(16, 0);

    public static final AvailableDateTimeRequest NOW_MONTH_REQUEST = new AvailableDateTimeRequest(
            NOW.getYear(),
            NOW.getMonthValue(),
            List.of(LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
                    LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
                    LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME),
                    LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
                    LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
                    LocalDateTime.of(NOW_PLUS_3_DAYS, THIRD_TIME),
                    LocalDateTime.of(NOW_PLUS_4_DAYS, FIRST_TIME),
                    LocalDateTime.of(NOW_PLUS_4_DAYS, SECOND_TIME),
                    LocalDateTime.of(NOW_PLUS_4_DAYS, THIRD_TIME)));

    public static final AvailableDateTimeRequest NEXT_MONTH_REQUEST = new AvailableDateTimeRequest(
            NOW_PLUS_1_MONTH.getYear(),
            NOW_PLUS_1_MONTH.getMonthValue(),
            List.of(LocalDateTime.of(NOW_PLUS_1_MONTH, FIRST_TIME),
                    LocalDateTime.of(NOW_PLUS_1_MONTH, SECOND_TIME),
                    LocalDateTime.of(NOW_PLUS_1_MONTH, THIRD_TIME)));

    public static final AvailableDateTimesRequest MONTH_REQUEST = new AvailableDateTimesRequest(
            List.of(NOW_MONTH_REQUEST));

    public static final AvailableDateTimesRequest MONTHS_REQUEST = new AvailableDateTimesRequest(
            List.of(NOW_MONTH_REQUEST, NEXT_MONTH_REQUEST));

    public static final List<LocalDateTime> COACH_AVAILABLE_TIME = List.of(
            LocalDateTime.of(NOW_PLUS_2_DAYS, FIRST_TIME),
            LocalDateTime.of(NOW_PLUS_2_DAYS, SECOND_TIME),
            LocalDateTime.of(NOW_PLUS_2_DAYS, THIRD_TIME),
            LocalDateTime.of(NOW_PLUS_3_DAYS, FIRST_TIME),
            LocalDateTime.of(NOW_PLUS_3_DAYS, SECOND_TIME),
            LocalDateTime.of(NOW_PLUS_3_DAYS, THIRD_TIME),
            LocalDateTime.of(NOW_PLUS_4_DAYS, FIRST_TIME),
            LocalDateTime.of(NOW_PLUS_4_DAYS, SECOND_TIME),
            LocalDateTime.of(NOW_PLUS_4_DAYS, THIRD_TIME));
}
