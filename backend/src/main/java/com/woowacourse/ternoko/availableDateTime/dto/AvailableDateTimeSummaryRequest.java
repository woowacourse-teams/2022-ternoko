package com.woowacourse.ternoko.availabledatetime.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateTimeSummaryRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime time;

    private AvailableDateTimeStatus availableDateTimeStatus;
}
