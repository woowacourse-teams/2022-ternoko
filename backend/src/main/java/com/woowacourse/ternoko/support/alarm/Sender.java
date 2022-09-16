package com.woowacourse.ternoko.support.alarm;

import com.woowacourse.ternoko.core.domain.interview.Interview;

public abstract class Sender {

    final String botToken;
    final String url;
    final String sendApi;

    protected Sender(final String botToken, final String url, final String sendApi) {
        this.botToken = botToken;
        this.url = url;
        this.sendApi = sendApi;
    }

    abstract void postCrewMessage(final SlackMessageType slackMessageType, final Interview interview);

    abstract void postCoachMessage(final SlackMessageType slackMessageType, final Interview interview);
}
