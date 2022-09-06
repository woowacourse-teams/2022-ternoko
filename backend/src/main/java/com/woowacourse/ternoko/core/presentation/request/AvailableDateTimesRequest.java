package com.woowacourse.ternoko.core.presentation.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateTimesRequest {

    private List<AvailableDateTimeRequest> calendarTimes;
}
