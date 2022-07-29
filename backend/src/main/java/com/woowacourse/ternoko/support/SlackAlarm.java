package com.woowacourse.ternoko.support;

import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.woowacourse.ternoko.domain.Interview;
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

    public void sendMessage(final Interview interview, final String message) throws Exception {
        LocalDateTime interviewStartTime = interview.getInterviewStartTime();
        String coachNickname = interview.getCoach().getNickname();
        String crewNickname = interview.getCrew().getNickname();

        ChatPostMessageRequest crewRequest = ChatPostMessageRequest.builder()
                .text(String.format(message, coachNickname, crewNickname, interviewStartTime))
                .channel(interview.getCrew().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(crewRequest);

        ChatPostMessageRequest coachRequest = ChatPostMessageRequest.builder()
                .text(String.format(message, crewNickname, coachNickname, interviewStartTime))
                .channel(interview.getCoach().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(coachRequest);
    }
}
