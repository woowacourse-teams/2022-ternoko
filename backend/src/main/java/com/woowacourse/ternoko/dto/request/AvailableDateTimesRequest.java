package com.woowacourse.ternoko.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateTimesRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private List<LocalDateTime> calendarTimes;

    public List<AvailableDateTime> toAvailableDateTimes(final Member coach) {
        return calendarTimes.stream()
                .map(time -> new AvailableDateTime(coach, time))
                .collect(Collectors.toList());
    }
}
