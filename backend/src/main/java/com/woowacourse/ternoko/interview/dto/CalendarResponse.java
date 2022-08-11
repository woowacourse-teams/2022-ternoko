package com.woowacourse.ternoko.interview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.ternoko.interview.domain.Interview;
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

    public static CalendarResponse from(final Interview interview) {
        return CalendarResponse.calenderResponseBuilder()
                .id(interview.getId())
                .crewNickname(interview.getCrew().getNickname())
                .interviewStartTime(
                        interview.getInterviewStartTime())
                .interviewEndTime(
                        interview.getInterviewEndTime())
                .build();
    }
}
