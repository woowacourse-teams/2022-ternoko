package com.woowacourse.ternoko.core.domain.interview.event;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import org.springframework.context.ApplicationEvent;

public class InterviewCreatedEvent extends ApplicationEvent {

    private final Interview interview;

    public InterviewCreatedEvent(Object source, Interview interview) {
        super(source);
        this.interview = interview;
    }

    public Interview getInterview() {
        return interview;
    }
}
