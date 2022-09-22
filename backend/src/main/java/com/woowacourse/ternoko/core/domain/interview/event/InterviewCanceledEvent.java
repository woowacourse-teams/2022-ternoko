package com.woowacourse.ternoko.core.domain.interview.event;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import org.springframework.context.ApplicationEvent;

public class InterviewCanceledEvent extends ApplicationEvent {

    private final Interview interview;

    public InterviewCanceledEvent(Object source, Interview interview) {
        super(source);
        this.interview = interview;
    }

    public Interview getInterview() {
        return interview;
    }
}
