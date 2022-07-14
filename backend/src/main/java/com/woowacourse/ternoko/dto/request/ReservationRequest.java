package com.woowacourse.ternoko.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull
    private String crewNickname;
    @NotNull
    private LocalDateTime interviewDatetime;
    @NotNull
    private List<FormItemRequest> interviewQuestions;
}
