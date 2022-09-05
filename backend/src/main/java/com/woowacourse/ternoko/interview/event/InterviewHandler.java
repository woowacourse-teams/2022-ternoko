package com.woowacourse.ternoko.interview.event;

import com.woowacourse.ternoko.support.SlackAlarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InterviewHandler {

    @Autowired
    private SlackAlarm slackAlarm;

    @EventListener
    public void create(InterviewCreatedEvent interviewCreatedEvent) throws Exception {
        slackAlarm.sendCreateMessage(interviewCreatedEvent.getInterview());
    }

    @EventListener
    public void update(InterviewUpdatedEvent interviewUpdatedEvent) throws Exception {
        slackAlarm.sendUpdateMessage(interviewUpdatedEvent.getOrigin(), interviewUpdatedEvent.getUpdate());
    }

    @EventListener
    public void canceled(InterviewCanceldEvent interviewCanceldEvent) throws Exception {
        slackAlarm.sendCancelMessage(interviewCanceldEvent.getInterview());
    }

    @EventListener
    public void delete(InterviewDeletedEvent interviewDeletedEvent) throws Exception {
        slackAlarm.sendDeleteMessage(interviewDeletedEvent.getInterview());
    }
}
