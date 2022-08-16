package com.woowacourse.ternoko.support;

import com.woowacourse.ternoko.dto.EmailDto;
import com.woowacourse.ternoko.interview.dto.FormItemDto;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.mail.SimpleMailMessage;

public class EmailGenerator {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E) hh시 mm분");

    public static SimpleMailMessage generate(final EmailDto emailDto) {
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

    private static String generateContent(final EmailDto emailDto) {
        final StringBuilder contentBuilder = new StringBuilder();
        generateContentHeader(emailDto, contentBuilder);
        generateContentFormItem(emailDto.getFormItems(), contentBuilder);
        return contentBuilder.toString();
    }

    private static void generateContentHeader(final EmailDto emailDto, final StringBuilder contentBuilder) {
        contentBuilder.append(String.format("안녕하세요 %s. %s 면담신청 합니다",
                        emailDto.getCoachNickname(),
                        emailDto.getCrewNickname()))
                .append(System.lineSeparator())
                .append(String.format("면담 일시 : %s", dateFormat.format(emailDto.getInterviewStartTime())))
                .append(System.lineSeparator());
    }

    private static void generateContentFormItem(final List<FormItemDto> formItemsDto,
                                                final StringBuilder contentBuilder) {
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
