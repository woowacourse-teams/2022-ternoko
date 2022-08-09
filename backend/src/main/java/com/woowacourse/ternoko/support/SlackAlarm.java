package com.woowacourse.ternoko.support;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.interview.domain.Interview;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlackAlarm {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E) HH시 mm분");

    private final String botToken;
    private final MethodsClientImpl slackMethodClient;

    public SlackAlarm(final MethodsClientImpl slackMethodClient, @Value("${slack.botToken}") final String botToken) {
        this.slackMethodClient = slackMethodClient;
        this.botToken = botToken;
    }

    public void sendMessage(final Interview interview, final String message) throws Exception {
        postMessage(message, interview.getCrew(), interview.getCoach(), interview.getInterviewStartTime());
        postMessage(message, interview.getCoach(), interview.getCrew(), interview.getInterviewStartTime());
    }

    private void postMessage(final String message,
                             final Member receiver,
                             final Member member,
                             final LocalDateTime interviewStartTime)
            throws IOException, SlackApiException {
        final ChatPostMessageRequest crewRequest = ChatPostMessageRequest.builder()
                .text(String.format(message,
                        receiver.getNickname(),
                        member.getNickname(),
                        DATE_FORMAT.format(interviewStartTime)))
                .channel(receiver.getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(crewRequest);
    }
}
