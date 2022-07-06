package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderMethodName = "reservationResponseBuilder")
public class ReservationResponse {

    private final Long id;
    private final String coachNickname;
    private final String crewNickname;
    private final LocalDate interviewDate;
    private final LocalTime interviewStartTime;
    private final LocalTime interviewEndTime;
    private final String location;

    public static ReservationResponse from(final Reservation reservation) {
        return ReservationResponse.reservationResponseBuilder()
                .id(reservation.getId())
                .coachNickname(reservation.getInterview().getCoach().getNickname())
                .crewNickname(reservation.getInterview().getCrewNickname())
                .interviewDate(reservation.getInterview().getInterviewDate())
                .interviewStartTime(reservation.getInterview().getInterviewStartTime())
                .interviewEndTime(reservation.getInterview().getInterviewEndTime())
                .location(reservation.getInterview().getLocation().getValue())
                .build();
    }
}
