package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.Interview;
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
    private final String interviewStartTime;
    private final String interviewEndTime;

    public static CalendarResponse from(Interview interview) {
        return CalendarResponse.calenderResponseBuilder()
                .id(interview.getId())
                .crewNickname(interview.getCrewNickname())
                .interviewStartTime(
                        interview.getInterviewStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .interviewEndTime(
                        interview.getInterviewEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
