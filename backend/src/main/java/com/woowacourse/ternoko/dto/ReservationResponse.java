package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.Reservation;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderMethodName = "reservationResponseBuilder")
public class ReservationResponse {

    private final Long id;
    private final String coachNickname;
    private final String imageUrl;
    private final String crewNickname;
    private final String interviewStartTime;
    private final String interviewEndTime;
    private final List<FormItemDto> interviewQuestions;

    public static ReservationResponse from(final Reservation reservation) {
        final Interview interview = reservation.getInterview();
        final List<FormItemDto> formItemDtos = interview.getFormItems().stream()
                .map(FormItemDto::from)
                .collect(Collectors.toList());

        return ReservationResponse.reservationResponseBuilder()
                .id(reservation.getId())
                .coachNickname(interview.getCoach().getNickname())
                .imageUrl(interview.getCoach().getImageUrl())
                .crewNickname(interview.getCrewNickname())
                .interviewStartTime(
                        interview.getInterviewStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .interviewEndTime(
                        interview.getInterviewEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .interviewQuestions(formItemDtos)
                .build();
    }
}
