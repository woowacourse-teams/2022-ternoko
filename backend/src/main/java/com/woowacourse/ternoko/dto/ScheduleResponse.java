package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.Interview;
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

    public static ScheduleResponse from(List<Interview> interviews) {
        return new ScheduleResponse(interviews.stream()
                .map(CalendarResponse::from)
                .collect(Collectors.toList()));
    }
}
