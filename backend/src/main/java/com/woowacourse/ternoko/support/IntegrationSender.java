package com.woowacourse.ternoko.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class IntegrationSender extends Sender {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final WebClient webClient;

    public IntegrationSender(final String botToken, final String url, final String sendApi) {
        super(botToken, url, sendApi);
        this.webClient = WebClient.create(url);
    }

    @Override
    public void postCrewMessage(final SlackMessageType slackMessageType, final Interview interview) {
        postWebClient(SlackMessageGenerator.getCrewMessageRequest(slackMessageType, interview, botToken));

    }

    @Override
    public void postCoachMessage(final SlackMessageType slackMessageType, final Interview interview) {
        postWebClient(SlackMessageGenerator.getCoachMessageRequest(slackMessageType, interview, botToken));
    }

    private void postWebClient(ChatPostMessageRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        ClientResponse block = null;
        try {
            block = webClient.post()
                    .uri(sendApi)
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                    .header(AUTHORIZATION_HEADER, botToken)
                    .bodyValue(objectMapper.writeValueAsString(request))
                    .exchange().block();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
