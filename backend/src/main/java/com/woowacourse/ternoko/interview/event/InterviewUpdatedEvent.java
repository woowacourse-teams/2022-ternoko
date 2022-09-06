package com.woowacourse.ternoko.interview.event;

import com.woowacourse.ternoko.interview.domain.Interview;
import org.springframework.context.ApplicationEvent;

public class InterviewUpdatedEvent extends ApplicationEvent {

    private final Interview origin;
    private final Interview update;

    public InterviewUpdatedEvent(Object source, Interview origin, Interview update) {
        super(source);
        this.origin = origin;
        this.update = update;
    }

    public Interview getOrigin() {
        return origin;
    }

    public Interview getUpdate() {
        return update;
    }
}
