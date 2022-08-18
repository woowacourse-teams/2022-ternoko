package com.woowacourse.ternoko.interview.dto;

import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.interview.domain.Interview;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "alarmResponseBuilder")
public class AlarmResponse {

    private Long interviewId;
    private Crew crew;
    private Coach coach;
    private LocalDateTime interviewStartTime;

    public static AlarmResponse from(Interview interview) {
        return AlarmResponse.alarmResponseBuilder()
                .interviewId(interview.getId())
                .coach(interview.getCoach())
                .crew(interview.getCrew())
                .interviewStartTime(interview.getInterviewStartTime())
                .build();
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public Crew getCrew() {
        return crew;
    }

    public Coach getCoach() {
        return coach;
    }

    public LocalDateTime getInterviewStartTime() {
        return interviewStartTime;
    }
}
