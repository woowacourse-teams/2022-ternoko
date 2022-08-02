package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.FormItem;
import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.util.EmailSenderDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    @Value("${spring.mail.username}")
    private String from;
    private Member coach;
    private String crewNickname;
    private LocalDateTime interviewStartTime;
    private List<FormItem> formItems;

    public EmailDto(final Member coach,
                    final String crewNickname,
                    final LocalDateTime interviewStartTime,
                    final List<FormItem> formItems) {
        this.coach = coach;
        this.crewNickname = crewNickname;
        this.interviewStartTime = interviewStartTime;
        this.formItems = formItems;
    }

    public static List<EmailDto> from(final List<Interview> interviews) {
        return interviews.stream()
                .map(interview -> new EmailDto(interview.getCoach(),
                        interview.getCrew().getNickname(),
                        interview.getInterviewStartTime(),
                        interview.getFormItems()))
                .collect(Collectors.toList());
    }

    public static EmailSenderDto toEmailDto(final EmailDto emailDto) {
        String coachNickname = emailDto.coach.getNickname();
        String crewNickname = emailDto.getCrewNickname();
        String subject = String.format("[면담 사전메일] 안녕하세요 %s. %s 면담신청 합니다", coachNickname, crewNickname);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder contentBuilder = stringBuilder.append(
                        String.format("안녕하세요 %s. %s 면담신청 합니다", coachNickname, crewNickname))
                .append(System.lineSeparator())
                .append(String.format("면담 일시 : %s", emailDto.interviewStartTime))
                .append(System.lineSeparator())
                .append("사전 질문 내용")
                .append(System.lineSeparator());

        for (FormItem formItem : emailDto.getFormItems()) {
            contentBuilder.append(String.format("- %s", formItem.getQuestion()))
                    .append(System.lineSeparator())
                    .append(formItem.getAnswer())
                    .append(System.lineSeparator());
        }

        return new EmailSenderDto(
                emailDto.from,
                emailDto.coach.getEmail(),
                subject,
                contentBuilder.toString()
        );
    }
}
