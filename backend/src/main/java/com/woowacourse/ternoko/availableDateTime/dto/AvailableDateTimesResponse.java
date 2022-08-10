package com.woowacourse.ternoko.availabledatetime.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateTimesResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private List<LocalDateTime> calendarTimes;

    public static AvailableDateTimesResponse from(final List<AvailableDateTime> availableDateTimes) {
        return new AvailableDateTimesResponse(availableDateTimes.stream()
                .map(AvailableDateTime::getLocalDateTime)
                .sorted()
                .collect(Collectors.toList()));
    }
}
