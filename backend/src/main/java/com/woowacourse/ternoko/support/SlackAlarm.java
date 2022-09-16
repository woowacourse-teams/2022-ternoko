package com.woowacourse.ternoko.support;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlackAlarm {

    private final Sender sender;

    public SlackAlarm(@Value("${slack.botToken}") final String botToken,
                      @Value("${slack.url}") final String url,
                      @Value("${slack.sendApi}") final String sendApi,
                      @Value("${slack.destination}") final String destination) {
        this.sender = AlarmSenderPicker.of(destination, botToken, sendApi, url);
    }

    public void sendCreateMessage(final Interview interview) {
        sender.postCrewMessage(SlackMessageType.CREW_CREATE, interview);
        sender.postCoachMessage(SlackMessageType.CREW_CREATE, interview);
    }

    public void sendUpdateMessage(final Interview origin, final Interview update) {
        if (!origin.getCoach().getNickname().equals(update.getCoach().getNickname())) {
            sender.postCrewMessage(SlackMessageType.CREW_UPDATE, update);
            sender.postCoachMessage(SlackMessageType.CREW_DELETE, origin);
            sender.postCoachMessage(SlackMessageType.CREW_CREATE, update);
            return;
        }
        sender.postCrewMessage(SlackMessageType.CREW_UPDATE, update);
        sender.postCoachMessage(SlackMessageType.CREW_UPDATE, update);
    }

    public void sendDeleteMessage(final Interview interview) {
        sender.postCrewMessage(SlackMessageType.CREW_DELETE, interview);
        sender.postCoachMessage(SlackMessageType.CREW_DELETE, interview);
    }

    public void sendCancelMessage(final Interview interview) {
        sender.postCrewMessage(SlackMessageType.COACH_CANCEL, interview);
        sender.postCoachMessage(SlackMessageType.COACH_CANCEL, interview);
    }
}
