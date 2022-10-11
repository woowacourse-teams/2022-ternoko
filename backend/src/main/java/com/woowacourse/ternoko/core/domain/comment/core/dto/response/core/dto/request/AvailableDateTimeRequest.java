package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.request;

import com.woowacourse.ternoko.core.dto.request.AvailableDateTimeSummaryRequest;
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
