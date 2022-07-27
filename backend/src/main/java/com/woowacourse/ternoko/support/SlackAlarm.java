package com.woowacourse.ternoko.support;

import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
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

    public void sendMessage(final String userId) throws Exception {

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .text("test 메시지 입니다.")
                .channel(userId)
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(request);
    }
}
