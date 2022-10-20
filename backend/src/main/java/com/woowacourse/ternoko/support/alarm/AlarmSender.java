//package com.woowacourse.ternoko.support.alarm;
//
//import static com.woowacourse.ternoko.support.alarm.SlackMessageType.COACH_CANCEL;
//import static com.woowacourse.ternoko.support.alarm.SlackMessageType.CREW_CREATE;
//import static com.woowacourse.ternoko.support.alarm.SlackMessageType.CREW_DELETE;
//import static com.woowacourse.ternoko.support.alarm.SlackMessageType.CREW_UPDATE;
//
//import lombok.RequiredArgsConstructor;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class AlarmSender {
//
//    private final AlarmResponseCache alarmResponseCache;
//    private final SlackAlarm alarm;
//
//    @AfterReturning("@annotation(com.woowacourse.ternoko.auth.presentation.annotation.SlackAlarm)")
//    public void sendAlarm(JoinPoint joinPoint) throws Exception {
//        if (alarmResponseCache.getMessageType() == (CREW_CREATE)) {
//            alarm.sendCreateMessage(alarmResponseCache.getOrigin());
//            return;
//        }
//        if (alarmResponseCache.getMessageType() == (CREW_UPDATE)) {
//            alarm.sendUpdateMessage(alarmResponseCache.getOrigin(), alarmResponseCache.getUpdate());
//            return;
//        }
//        if (alarmResponseCache.getMessageType() == (COACH_CANCEL)) {
//            alarm.sendCancelMessage(alarmResponseCache.getOrigin());
//            return;
//        }
//        if (alarmResponseCache.getMessageType() == (CREW_DELETE)) {
//            alarm.sendDeleteMessage(alarmResponseCache.getOrigin());
//            return;
//        }
//    }
//}
//
//
//
