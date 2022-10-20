//package com.woowacourse.ternoko.support.application.response;
//
//import com.woowacourse.ternoko.core.domain.interview.Interview;
//import com.woowacourse.ternoko.core.dto.response.FormItemResponse;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class EmailResponse {
//    private String from;
//    private String coachNickname;
//    private String coachEmail;
//    private String crewNickname;
//    private LocalDateTime interviewStartTime;
//    private List<FormItemResponse> formItems;
//
//    public static EmailResponse of(final Interview interview,
//                                   final String from) {
//        final List<FormItemResponse> formItemDtos = interview.getFormItems().stream()
//                .map(FormItemResponse::from)
//                .collect(Collectors.toList());
//
//        return new EmailResponse(
//                from,
//                interview.getCoach().getNickname(),
//                interview.getCoach().getEmail(),
//                interview.getCrew().getNickname(),
//                interview.getInterviewStartTime(),
//                formItemDtos
//        );
//    }
//}
