package com.woowacourse.ternoko.interview.event;

import com.woowacourse.ternoko.support.SlackAlarm;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InterviewHandler {

    private final SlackAlarm slackAlarm;

    public InterviewHandler(SlackAlarm slackAlarm) {
        this.slackAlarm = slackAlarm;
    }

    @EventListener
    public void create(InterviewCreatedEvent interviewCreatedEvent) throws Exception {
        slackAlarm.sendCreateMessage(interviewCreatedEvent.getInterview());
    }

    @EventListener
    public void update(InterviewUpdatedEvent interviewUpdatedEvent) throws Exception {
        slackAlarm.sendUpdateMessage(interviewUpdatedEvent.getOrigin(), interviewUpdatedEvent.getUpdate());
    }

    @EventListener
    public void canceled(InterviewCanceledEvent interviewCanceledEvent) throws Exception {
        slackAlarm.sendCancelMessage(interviewCanceledEvent.getInterview());
    }

    @EventListener
    public void delete(InterviewDeletedEvent interviewDeletedEvent) throws Exception {
        slackAlarm.sendDeleteMessage(interviewDeletedEvent.getInterview());
    }
}
