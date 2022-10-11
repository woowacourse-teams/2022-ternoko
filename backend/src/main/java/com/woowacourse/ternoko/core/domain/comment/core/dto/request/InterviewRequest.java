package com.woowacourse.ternoko.core.domain.comment.core.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.ternoko.core.dto.request.FormItemRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRequest {

    private Long coachId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime interviewDatetime;
    private List<FormItemRequest> interviewQuestions;
}
