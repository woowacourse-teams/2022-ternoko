package com.woowacourse.ternoko.interview.event;

import com.woowacourse.ternoko.interview.domain.Interview;
import org.springframework.context.ApplicationEvent;

public class InterviewCanceldEvent extends ApplicationEvent {

    private Interview interview;

    public InterviewCanceldEvent(Object source) {
        super(source);
    }

    public InterviewCanceldEvent(Object source, Interview interview) {
        super(source);
        this.interview = interview;
    }

    public Interview getInterview() {
        return interview;
    }
}
