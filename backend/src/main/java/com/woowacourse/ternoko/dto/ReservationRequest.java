package com.woowacourse.ternoko.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    private String crewNickname;
    private LocalDateTime interviewDatetime;
    private List<FormItemDto> interviewQuestions;
}
