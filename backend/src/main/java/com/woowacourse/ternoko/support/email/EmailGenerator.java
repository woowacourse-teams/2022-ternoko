//package com.woowacourse.ternoko.support.email;
//
//import com.woowacourse.ternoko.core.dto.response.FormItemResponse;
//import com.woowacourse.ternoko.support.application.response.EmailResponse;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import org.springframework.mail.SimpleMailMessage;
//
//public class EmailGenerator {
//
//    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E) hh시 mm분");
//
//    public static SimpleMailMessage generate(final EmailResponse emailResponse) {
//        final String coachNickname = emailResponse.getCoachNickname();
//        final String crewNickname = emailResponse.getCrewNickname();
//        final String subject = String.format("[면담 사전메일] 안녕하세요 %s. %s 면담신청 합니다", coachNickname, crewNickname);
//        final String content = generateContent(emailResponse);
//
//        final SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(emailResponse.getFrom());
//        message.setTo(emailResponse.getCoachEmail());
//        message.setSubject(subject);
//        message.setText(content);
//        return message;
//    }
//
//    private static String generateContent(final EmailResponse emailResponse) {
//        final StringBuilder contentBuilder = new StringBuilder();
//        generateContentHeader(emailResponse, contentBuilder);
//        generateContentFormItem(emailResponse.getFormItems(), contentBuilder);
//        return contentBuilder.toString();
//    }
//
//    private static void generateContentHeader(final EmailResponse emailResponse, final StringBuilder contentBuilder) {
//        contentBuilder.append(String.format("안녕하세요 %s. %s 면담신청 합니다",
//                        emailResponse.getCoachNickname(),
//                        emailResponse.getCrewNickname()))
//                .append(System.lineSeparator())
//                .append(String.format("면담 일시 : %s", dateFormat.format(emailResponse.getInterviewStartTime())))
//                .append(System.lineSeparator());
//    }
//
//    private static void generateContentFormItem(final List<FormItemResponse> formItemsDto,
//                                                final StringBuilder contentBuilder) {
//        contentBuilder.append("사전 질문 내용")
//                .append(System.lineSeparator());
//        for (final FormItemResponse formItem : formItemsDto) {
//            contentBuilder.append(String.format("- %s", formItem.getQuestion()))
//                    .append(System.lineSeparator())
//                    .append(formItem.getAnswer())
//                    .append(System.lineSeparator());
//        }
//    }
//}
