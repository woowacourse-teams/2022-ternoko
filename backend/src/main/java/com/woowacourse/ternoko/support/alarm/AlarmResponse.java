//package com.woowacourse.ternoko.support.alarm;
//
//import com.woowacourse.ternoko.core.domain.interview.Interview;
//import java.time.LocalDateTime;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//public class AlarmResponse {
//    private String coachNickname;
//    private String crewNickname;
//    private Long coachId;
//    private Long crewId;
//    private String coachUserId;
//    private String crewUserId;
//    private String coachImageUrl;
//    private String crewImageUrl;
//    private LocalDateTime startInterviewTime;
//
//    public AlarmResponse(final String coachNickname, final String crewNickname, final Long coachId, final Long crewId,
//                         final String coachUserId,
//                         final String crewUserId, final String coachImageUrl, final String crewImageUrl,
//                         final LocalDateTime startInterviewTime) {
//        this.coachNickname = coachNickname;
//        this.crewNickname = crewNickname;
//        this.coachId = coachId;
//        this.crewId = crewId;
//        this.coachUserId = coachUserId;
//        this.crewUserId = crewUserId;
//        this.coachImageUrl = coachImageUrl;
//        this.crewImageUrl = crewImageUrl;
//        this.startInterviewTime = startInterviewTime;
//    }
//
//    public static AlarmResponse from(final Interview interview) {
//        return new AlarmResponse(interview.getCoach().getNickname(),
//                interview.getCrew().getNickname(),
//                interview.getCoach().getId(),
//                interview.getCrew().getId(),
//                interview.getCoach().getUserId(),
//                interview.getCrew().getUserId(),
//                interview.getCoach().getImageUrl(),
//                interview.getCrew().getImageUrl(),
//                interview.getInterviewStartTime());
//    }
//}
