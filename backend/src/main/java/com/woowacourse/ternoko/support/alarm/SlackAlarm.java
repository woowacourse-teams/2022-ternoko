//package com.woowacourse.ternoko.support.alarm;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SlackAlarm {
//
//    private final Sender sender;
//
//    public SlackAlarm(@Value("${slack.botToken}") final String botToken,
//                      @Value("${slack.url}") final String url,
//                      @Value("${slack.sendApi}") final String sendApi,
//                      @Value("${slack.destination}") final String destination) {
//        this.sender = AlarmSenderPicker.of(destination, botToken, sendApi, url);
//    }
//
//    public void sendCreateMessage(final AlarmResponse response) {
//        sender.postCrewMessage(SlackMessageType.CREW_CREATE, response);
//        sender.postCoachMessage(SlackMessageType.CREW_CREATE, response);
//    }
//
//    public void sendUpdateMessage(final AlarmResponse origin, final AlarmResponse update) {
//        if (!origin.getCoachNickname().equals(update.getCoachNickname())) {
//            sender.postCrewMessage(SlackMessageType.CREW_UPDATE, update);
//            sender.postCoachMessage(SlackMessageType.CREW_DELETE, origin);
//            sender.postCoachMessage(SlackMessageType.CREW_CREATE, update);
//            return;
//        }
//        sender.postCrewMessage(SlackMessageType.CREW_UPDATE, update);
//        sender.postCoachMessage(SlackMessageType.CREW_UPDATE, update);
//    }
//
//    public void sendDeleteMessage(final AlarmResponse response) {
//        sender.postCrewMessage(SlackMessageType.CREW_DELETE, response);
//        sender.postCoachMessage(SlackMessageType.CREW_DELETE, response);
//    }
//
//    public void sendCancelMessage(final AlarmResponse response) {
//        sender.postCrewMessage(SlackMessageType.COACH_CANCEL, response);
//        sender.postCoachMessage(SlackMessageType.COACH_CANCEL, response);
//    }
//}
