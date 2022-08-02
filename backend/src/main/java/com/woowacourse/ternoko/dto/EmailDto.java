package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.Interview;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "emailDtoBuilder")
public class EmailDto {
    @Value("${spring.mail.username}")
    private String from;
    private String coachNickname;
    private String coachEmail;
    private String crewNickname;
    private LocalDateTime interviewStartTime;
    private List<FormItemDto> formItems;

    public static EmailDto from(final Interview interview) {
        final List<FormItemDto> formItemDtos = interview.getFormItems().stream()
                .map(FormItemDto::from)
                .collect(Collectors.toList());

        return EmailDto.emailDtoBuilder()
                .coachNickname(interview.getCoach().getNickname())
                .coachEmail(interview.getCoach().getEmail())
                .crewNickname(interview.getCrew().getNickname())
                .interviewStartTime(interview.getInterviewStartTime())
                .formItems(formItemDtos)
                .build();
    }
}
