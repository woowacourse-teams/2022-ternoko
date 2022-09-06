package com.woowacourse.ternoko.interview.event;

import com.woowacourse.ternoko.interview.domain.Interview;
import org.springframework.context.ApplicationEvent;

public class InterviewDeletedEvent extends ApplicationEvent {

    private final Interview interview;

    public InterviewDeletedEvent(Object source, Interview interview) {
        super(source);
        this.interview = interview;
    }

    public Interview getInterview() {
        return interview;
    }
}
