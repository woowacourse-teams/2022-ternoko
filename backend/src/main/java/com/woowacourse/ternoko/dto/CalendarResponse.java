package com.woowacourse.ternoko.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.ternoko.domain.Interview;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderMethodName = "calenderResponseBuilder")
public class CalendarResponse {

    private final Long id;
    private final String crewNickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime interviewStartTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime interviewEndTime;

    public static CalendarResponse from(final Interview interview) {
        return CalendarResponse.calenderResponseBuilder()
                .id(interview.getId())
                .crewNickname(interview.getCrewNickname())
                .interviewStartTime(
                        interview.getInterviewStartTime())
                .interviewEndTime(
                        interview.getInterviewEndTime())
                .build();
    }
}
