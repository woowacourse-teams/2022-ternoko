package com.woowacourse.ternoko.support;

import com.woowacourse.ternoko.dto.EmailDto;
import com.woowacourse.ternoko.interview.dto.FormItemDto;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailSender {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E) hh시 mm분");

    private JavaMailSender mailSender;

    public void send(final EmailDto emailDto) {
        mailSender.send(generate(emailDto));
    }

    private SimpleMailMessage generate(final EmailDto emailDto) {
        final String coachNickname = emailDto.getCoachNickname();
        final String crewNickname = emailDto.getCrewNickname();
        final String subject = String.format("[면담 사전메일] 안녕하세요 %s. %s 면담신청 합니다", coachNickname, crewNickname);
        final String content = generateContent(emailDto);

        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailDto.getFrom());
        message.setTo(emailDto.getCoachEmail());
        message.setSubject(subject);
        message.setText(content);
        return message;
    }

    private String generateContent(final EmailDto emailDto) {
        final StringBuilder contentBuilder = new StringBuilder();
        generateContentHeader(emailDto, contentBuilder);
        generateContentFormItem(emailDto.getFormItems(), contentBuilder);
        return contentBuilder.toString();
    }

    private void generateContentHeader(final EmailDto emailDto, final StringBuilder contentBuilder) {
        contentBuilder.append(String.format("안녕하세요 %s. %s 면담신청 합니다",
                        emailDto.getCoachNickname(),
                        emailDto.getCrewNickname()))
                .append(System.lineSeparator())
                .append(String.format("면담 일시 : %s", dateFormat.format(emailDto.getInterviewStartTime())))
                .append(System.lineSeparator());
    }

    private void generateContentFormItem(final List<FormItemDto> formItemsDto, final StringBuilder contentBuilder) {
        contentBuilder.append("사전 질문 내용")
                .append(System.lineSeparator());
        for (final FormItemDto formItem : formItemsDto) {
            contentBuilder.append(String.format("- %s", formItem.getQuestion()))
                    .append(System.lineSeparator())
                    .append(formItem.getAnswer())
                    .append(System.lineSeparator());
        }
    }
}
