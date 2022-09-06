package com.woowacourse.ternoko.core.application.response;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

    private List<CalendarResponse> calendar;

    public static ScheduleResponse from(final List<Interview> interviews) {
        return new ScheduleResponse(interviews.stream()
                .map(CalendarResponse::from)
                .collect(Collectors.toList()));
    }
}
