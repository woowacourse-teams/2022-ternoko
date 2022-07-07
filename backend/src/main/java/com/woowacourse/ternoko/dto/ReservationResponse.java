package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
@Builder(builderMethodName = "reservationResponseBuilder")
public class ReservationResponse {

    private final Long id;
    private final String coachNickname;
    private final String imageUrl;
    private final String crewNickname;
    private final LocalDate interviewDate;
    private final LocalTime interviewStartTime;
    private final LocalTime interviewEndTime;
    private final String location;

    public static ReservationResponse from(final Reservation reservation) {
        final Interview interview = reservation.getInterview();

        return ReservationResponse.reservationResponseBuilder()
                .id(reservation.getId())
                .coachNickname(interview.getCoach().getNickname())
                .imageUrl(interview.getCoach().getImageUrl())
                .crewNickname(interview.getCrewNickname())
                .interviewDate(interview.getInterviewDate())
                .interviewStartTime(interview.getInterviewStartTime())
                .interviewEndTime(interview.getInterviewEndTime())
                .location(interview.getLocation().getValue())
                .build();
    }
}
