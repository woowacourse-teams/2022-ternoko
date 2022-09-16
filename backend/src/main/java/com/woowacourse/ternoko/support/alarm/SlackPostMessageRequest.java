package com.woowacourse.ternoko.support.alarm;

import com.slack.api.model.Attachment;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SlackPostMessageRequest {

    private String channel;

    private String text;

    private List<Attachment> attachments;

    private boolean mrkdwn = true;

    public SlackPostMessageRequest() {
    }
}
