package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.core.dto.request;

import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarRequest {

    private List<AvailableDateTimeRequest> calendarTimes;
}
