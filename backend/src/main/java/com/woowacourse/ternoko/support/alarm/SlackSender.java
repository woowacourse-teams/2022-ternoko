//package com.woowacourse.ternoko.support.alarm;
//
//import com.slack.api.methods.SlackApiException;
//import com.slack.api.methods.impl.MethodsClientImpl;
//import com.slack.api.util.http.SlackHttpClient;
//import java.io.IOException;
//
//public class SlackSender extends Sender {
//
//    private final MethodsClientImpl slackClient;
//
//    public SlackSender(final String botToken, final String url, final String sendApi) {
//        super(botToken, url, sendApi);
//        this.slackClient = new MethodsClientImpl(new SlackHttpClient());
//    }
//
//    @Override
//    public void postCrewMessage(final SlackMessageType slackMessageType, final AlarmResponse response) {
//        try {
//            slackClient.chatPostMessage(
//                    SlackMessageGenerator.getCrewMessageRequest(slackMessageType, response, botToken));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (SlackApiException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void postCoachMessage(final SlackMessageType slackMessageType, final AlarmResponse response) {
//        try {
//            slackClient.chatPostMessage(
//                    SlackMessageGenerator.getCoachMessageRequest(slackMessageType, response, botToken));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (SlackApiException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
