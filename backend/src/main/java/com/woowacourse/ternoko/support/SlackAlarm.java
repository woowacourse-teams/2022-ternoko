package com.woowacourse.ternoko.support;

import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.woowacourse.ternoko.domain.Reservation;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlackAlarm {

    private final String botToken;

    private final MethodsClientImpl slackMethodClient;

    public SlackAlarm(final MethodsClientImpl slackMethodClient, @Value("${slack.botToken}") final String botToken) {
        this.slackMethodClient = slackMethodClient;
        this.botToken = botToken;
    }

    public void sendAlarmWhenCreatedReservationToCrew(final Reservation reservation) throws Exception {
        LocalDateTime interviewStartTime = reservation.getInterview().getInterviewStartTime();
        String coachNickname = reservation.getInterview().getCoach().getNickname();
        String crewNickname = reservation.getInterview().getCrew().getNickname();
        String msg = crewNickname + ", " + coachNickname + "과의 면담이 " + interviewStartTime + "에 예약되었습니다.";

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .text(msg)
                .channel(reservation.getInterview().getCrew().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(request);
    }

    public void sendAlarmWhenCreatedReservationToCoach(final Reservation reservation) throws Exception {
        LocalDateTime interviewStartTime = reservation.getInterview().getInterviewStartTime();
        String coachNickname = reservation.getInterview().getCoach().getNickname();
        String crewNickname = reservation.getInterview().getCrew().getNickname();
        String msg = coachNickname + ", " + crewNickname + "과의 면담이 " + interviewStartTime + "에 예약되었습니다.";

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .text(msg)
                .channel(reservation.getInterview().getCoach().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(request);
    }

    public void sendAlarmWhenUpdatedReservationToCrew(final Reservation reservation) throws Exception {
        LocalDateTime interviewStartTime = reservation.getInterview().getInterviewStartTime();
        String coachNickname = reservation.getInterview().getCoach().getNickname();
        String crewNickname = reservation.getInterview().getCrew().getNickname();
        String msg = crewNickname + ", " + coachNickname + "과의 면담 예약이 " + interviewStartTime + "로 수정되었습니다.";

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .text(msg)
                .channel(reservation.getInterview().getCrew().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(request);
    }

    public void sendAlarmWhenUpdatedReservationToCoach(final Reservation reservation) throws Exception {
        LocalDateTime interviewStartTime = reservation.getInterview().getInterviewStartTime();
        String coachNickname = reservation.getInterview().getCoach().getNickname();
        String crewNickname = reservation.getInterview().getCrew().getNickname();
        String msg = coachNickname + ", " + crewNickname + "과의 면담 예약이 " + interviewStartTime + "로 수정되었습니다.";

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .text(msg)
                .channel(reservation.getInterview().getCoach().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(request);
    }

    public void sendAlarmWhenDeletedReservationToCrew(final Reservation reservation) throws Exception {
        LocalDateTime interviewStartTime = reservation.getInterview().getInterviewStartTime();
        String coachNickname = reservation.getInterview().getCoach().getNickname();
        String crewNickname = reservation.getInterview().getCrew().getNickname();
        String msg = crewNickname + ", " + coachNickname + "과의 면담 예약(" + interviewStartTime + ")이 취소되었습니다.";

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .text(msg)
                .channel(reservation.getInterview().getCrew().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(request);
    }

    public void sendAlarmWhenDeletedReservationToCoach(final Reservation reservation) throws Exception {
        LocalDateTime interviewStartTime = reservation.getInterview().getInterviewStartTime();
        String coachNickname = reservation.getInterview().getCoach().getNickname();
        String crewNickname = reservation.getInterview().getCrew().getNickname();
        String msg = coachNickname + ", " + crewNickname + "과의 면담 예약(" + interviewStartTime + ")이 취소되었습니다.";

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .text(msg)
                .channel(reservation.getInterview().getCoach().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(request);
    }
}
