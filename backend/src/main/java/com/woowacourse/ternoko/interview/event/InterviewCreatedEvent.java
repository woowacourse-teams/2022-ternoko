package com.woowacourse.ternoko.interview.event;

import com.woowacourse.ternoko.interview.domain.Interview;
import org.springframework.context.ApplicationEvent;

public class InterviewCreatedEvent extends ApplicationEvent {

    private Interview interview;

    public InterviewCreatedEvent(Object source) {
        super(source);
    }

    public InterviewCreatedEvent(Object source, Interview interview) {
        super(source);
        this.interview = interview;
    }

    public Interview getInterview() {
        return interview;
    }
}
