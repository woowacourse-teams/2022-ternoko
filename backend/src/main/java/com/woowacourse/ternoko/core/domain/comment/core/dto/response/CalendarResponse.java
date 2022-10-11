package com.woowacourse.ternoko.core.domain.comment.core.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "calenderResponseBuilder")
public class CalendarResponse {

    private Long id;
    private String crewNickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime interviewStartTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime interviewEndTime;
    @JsonProperty(value = "status")
    private String interviewStatus;

    public static com.woowacourse.ternoko.core.dto.response.CalendarResponse from(final Interview interview) {
        return com.woowacourse.ternoko.core.dto.response.CalendarResponse.calenderResponseBuilder()
                .id(interview.getId())
                .crewNickname(interview.getCrew().getNickname())
                .interviewStartTime(interview.getInterviewStartTime())
                .interviewEndTime(interview.getInterviewEndTime())
                .interviewStatus(interview.getInterviewStatusType().name())
                .build();
    }
}
