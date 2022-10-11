package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateTimeResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime calendarTime;
    private AvailableDateTimeStatus status;
}
