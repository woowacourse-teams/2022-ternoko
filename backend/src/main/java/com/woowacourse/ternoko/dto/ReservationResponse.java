package com.woowacourse.ternoko.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "reservationResponseBuilder")
public class ReservationResponse {

    private Long id;
    private String coachNickname;
    private String imageUrl;
    private String crewNickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime interviewStartTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime interviewEndTime;

    private List<FormItemResponse> interviewQuestions;

    public static ReservationResponse from(final Reservation reservation) {
        final Interview interview = reservation.getInterview();
        final List<FormItemResponse> formItemResponses = interview.getFormItems().stream()
                .map(FormItemResponse::from)
                .collect(Collectors.toList());

        return ReservationResponse.reservationResponseBuilder()
                .id(reservation.getId())
                .coachNickname(interview.getCoach().getNickname())
                .imageUrl(interview.getCoach().getImageUrl())
                .crewNickname(interview.getCrewNickname())
                .interviewStartTime(interview.getInterviewStartTime())
                .interviewEndTime(interview.getInterviewEndTime())
                .interviewQuestions(formItemResponses)
                .build();
    }
}
