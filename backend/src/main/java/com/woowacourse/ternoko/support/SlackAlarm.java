package com.woowacourse.ternoko.support;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.ImageElement;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlackAlarm {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH시 mm분",
            Locale.KOREAN);
    private static final String TERNOKO_CREW_URL = "https://ternoko.site/";
    private static final String TERNOKO_COACH_URL = "https://ternoko.site/coach/home";
    private static final int ORIGIN_RESPONSE = 0;
    private static final int UPDATE_RESPONSE = 1;

    private final String botToken;
    private final MethodsClientImpl slackMethodClient;

    public SlackAlarm(final MethodsClientImpl slackMethodClient, @Value("${slack.botToken}") final String botToken) {
        this.slackMethodClient = slackMethodClient;
        this.botToken = botToken;
    }

    public void sendCreateMessage(final Interview interview) throws Exception {
        postCrewMessage(SlackMessageType.CREW_CREATE, interview);
        postCoachMessage(SlackMessageType.CREW_CREATE, interview);
    }

    public void sendUpdateMessage(Interview origin, Interview update) throws Exception {
        if (!origin.getCoach().getNickname().equals(update.getCoach().getNickname())) {
            postCrewMessage(SlackMessageType.CREW_UPDATE, update);
            postCoachMessage(SlackMessageType.CREW_DELETE, origin);
            postCoachMessage(SlackMessageType.CREW_CREATE, update);
            return;
        }
        postCrewMessage(SlackMessageType.CREW_UPDATE, update);
        postCoachMessage(SlackMessageType.CREW_UPDATE, update);
    }

    public void sendDeleteMessage(final Interview interview) throws Exception {
        postCrewMessage(SlackMessageType.CREW_DELETE, interview);
        postCoachMessage(SlackMessageType.CREW_DELETE, interview);
    }

    public void sendCancelMessage(final Interview interview) throws Exception {
        postCrewMessage(SlackMessageType.COACH_CANCEL, interview);
        postCoachMessage(SlackMessageType.COACH_CANCEL, interview);
    }

    private void postCrewMessage(final SlackMessageType slackMessageType,
                                 final Interview interview) throws IOException, SlackApiException {
        final ChatPostMessageRequest crewRequest = ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCrewPreviewMessage(), interview.getCoach().getNickname()))
                .attachments(generateAttachment(slackMessageType, interview, TERNOKO_CREW_URL))
                .channel(interview.getCrew().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(crewRequest);
    }

    private void postCoachMessage(final SlackMessageType slackMessageType,
                                  final Interview interview) throws IOException, SlackApiException {
        final ChatPostMessageRequest coachRequest = ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCoachPreviewMessage(), interview.getCrew().getNickname()))
                .attachments(generateAttachment(slackMessageType, interview, TERNOKO_COACH_URL))
                .channel(interview.getCoach().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(coachRequest);
    }

    private List<Attachment> generateAttachment(final SlackMessageType slackMessageType,
                                                final Interview interview,
                                                final String homeUrl) {
        return List.of(Attachment.builder()
                .color(slackMessageType.getColor())
                .blocks(List.of(HeaderBlock.builder()
                                        .text(PlainTextObject.builder().text(slackMessageType.getAttachmentMessage()).build())
                                        .build(),
                                DividerBlock.builder().build(),
                                SectionBlock.builder().fields(
                                        List.of(MarkdownTextObject.builder()
                                                        .text(":clock3: *면담일시*"
                                                                + System.lineSeparator()
                                                                + DATE_FORMAT.format(interview.getInterviewStartTime()))
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(":smiley: *크루*"
                                                                + System.lineSeparator()
                                                                + interview.getCrew().getNickname())
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(" ")
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(":smiley: *코치*"
                                                                + System.lineSeparator()
                                                                + interview.getCoach().getNickname())
                                                        .build())
                                ).build(),
                                ContextBlock.builder().elements(List.of(
                                                MarkdownTextObject.builder()
                                                        .text("<" + homeUrl + "|터놓고로 이동>").build(),
                                                ImageElement.builder()
                                                        .imageUrl(interview.getCoach().getImageUrl())
                                                        .altText("코치 이미지")
                                                        .build(),
                                                ImageElement.builder()
                                                        .imageUrl(interview.getCrew().getImageUrl())
                                                        .altText("크루 이미지")
                                                        .build()))
                                        .build()
                        )
                ).build());
    }
}
