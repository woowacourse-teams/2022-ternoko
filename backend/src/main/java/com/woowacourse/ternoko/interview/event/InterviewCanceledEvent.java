package com.woowacourse.ternoko.interview.event;

import com.woowacourse.ternoko.interview.domain.Interview;
import org.springframework.context.ApplicationEvent;

public class InterviewCanceledEvent extends ApplicationEvent {

    private Interview interview;

    public InterviewCanceledEvent(Object source) {
        super(source);
    }

    public InterviewCanceledEvent(Object source, Interview interview) {
        super(source);
        this.interview = interview;
    }

    public Interview getInterview() {
        return interview;
    }
}
