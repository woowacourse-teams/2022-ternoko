package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.Interview;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private String from;
    private String coachNickname;
    private String coachEmail;
    private String crewNickname;
    private LocalDateTime interviewStartTime;
    private List<FormItemDto> formItems;

    public static EmailDto of(final Interview interview,
                              final String from) {
        final List<FormItemDto> formItemDtos = interview.getFormItems().stream()
                .map(FormItemDto::from)
                .collect(Collectors.toList());

        return new EmailDto(
                from,
                interview.getCoach().getNickname(),
                interview.getCoach().getEmail(),
                interview.getCrew().getNickname(),
                interview.getInterviewStartTime(),
                formItemDtos
        );
    }
}
