//package com.woowacourse.ternoko.support.alarm;
//
//public class AlarmSenderPicker {
//
//    public static final String DEVELOP = "develop";
//    public static final String INTEGRATION = "ori";
//
//    public static Sender of(final String destination,
//                            final String botToken,
//                            final String sendApi,
//                            final String url) {
//        if (destination.equals(DEVELOP)) {
//            return new SlackSender(botToken, url, sendApi);
//        }
//        if (destination.equals(INTEGRATION)) {
//            return new IntegrationSender(botToken, url, sendApi);
//        }
//        throw new IllegalArgumentException("존재하지 않는 서버입니다.");
//    }
//}
