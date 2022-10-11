package com.woowacourse.ternoko.core.domain.comment.core.dto.response;

import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.dto.response.AvailableDateTimeResponse;
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

    public static com.woowacourse.ternoko.core.dto.response.AvailableDateTimesResponse from(final List<AvailableDateTime> availableDateTimes) {
        return new com.woowacourse.ternoko.core.dto.response.AvailableDateTimesResponse(availableDateTimes.stream()
                .map(data -> new AvailableDateTimeResponse(data.getLocalDateTime(),
                        data.getAvailableDateTimeStatus()))
                .collect(Collectors.toList()));
    }
}
