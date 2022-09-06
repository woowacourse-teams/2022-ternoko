package com.woowacourse.ternoko.core.presentation.request;

import com.woowacourse.ternoko.core.presentation.request.AvailableDateTimeRequest;
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
