package com.woowacourse.ternoko.availabledatetime.dto;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateTimesResponse {

    private List<AvailableDateTimeResponse> calendarTimes;

    public static AvailableDateTimesResponse of(final List<AvailableDateTime> availableDateTimes) {
        return new AvailableDateTimesResponse(availableDateTimes.stream()
                .map(data -> new AvailableDateTimeResponse(data.getLocalDateTime(),
                        data.getAvailableDateTimeStatus()))
                .collect(Collectors.toList()));
    }
}
