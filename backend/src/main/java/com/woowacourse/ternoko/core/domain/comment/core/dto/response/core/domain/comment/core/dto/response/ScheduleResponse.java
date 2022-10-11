package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.core.dto.response;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.dto.response.CalendarResponse;
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

    public static com.woowacourse.ternoko.core.dto.response.ScheduleResponse from(final List<Interview> interviews) {
        return new com.woowacourse.ternoko.core.dto.response.ScheduleResponse(interviews.stream()
                .map(CalendarResponse::from)
                .collect(Collectors.toList()));
    }
}
