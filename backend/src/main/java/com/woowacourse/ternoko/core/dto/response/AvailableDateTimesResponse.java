package com.woowacourse.ternoko.core.dto.response;

import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
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

    public static AvailableDateTimesResponse from(final List<AvailableDateTime> availableDateTimes) {
        return new AvailableDateTimesResponse(availableDateTimes.stream()
                .map(data -> new AvailableDateTimeResponse(data.getId(),
                        data.getLocalDateTime(),
                        data.getAvailableDateTimeStatus()))
                .collect(Collectors.toList()));
    }
}
