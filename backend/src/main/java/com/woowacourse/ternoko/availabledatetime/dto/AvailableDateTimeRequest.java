package com.woowacourse.ternoko.availabledatetime.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateTimeRequest {

    private int year;
    private int month;
    private List<AvailableDateTimeSummaryRequest> times;
}
