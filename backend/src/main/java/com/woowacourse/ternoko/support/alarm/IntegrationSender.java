//package com.woowacourse.ternoko.support.alarm;
//
//import com.slack.api.methods.request.chat.ChatPostMessageRequest;
//import org.springframework.http.MediaType;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//public class IntegrationSender extends Sender {
//
//    private static final String AUTHORIZATION_HEADER = "Authorization";
//
//    private final RestTemplate restTemplate;
//
//    public IntegrationSender(final String botToken, final String url, final String sendApi) {
//        super(botToken, url, sendApi);
//        this.restTemplate = new RestTemplate();
//    }
//
//    @Override
//    public void postCrewMessage(final SlackMessageType slackMessageType, final AlarmResponse response) {
//        postRestTemplate(SlackMessageGenerator.getCrewMessageRequest(slackMessageType, response, botToken));
//    }
//
//    @Override
//    public void postCoachMessage(final SlackMessageType slackMessageType, final AlarmResponse response) {
//        postRestTemplate(SlackMessageGenerator.getCoachMessageRequest(slackMessageType, response, botToken));
//    }
//
//    private void postRestTemplate(ChatPostMessageRequest request) {
//        RequestEntity<ChatPostMessageRequest> requestRequestEntity = RequestEntity
//                .post(url + sendApi)
//                .contentType(MediaType.APPLICATION_JSON)
//                .header(AUTHORIZATION_HEADER, botToken)
//                .body(request);
//
//        ResponseEntity<String> exchange = restTemplate.exchange(requestRequestEntity, String.class);
//    }
//}
