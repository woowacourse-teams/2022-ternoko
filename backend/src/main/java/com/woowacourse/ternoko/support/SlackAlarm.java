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
import com.woowacourse.ternoko.interview.dto.AlarmResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
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

    public void sendCreateMessage(final AlarmResponse alarmResponse) throws Exception {
        postCrewMessage(SlackMessageType.CREW_CREATE, alarmResponse);
        postCoachMessage(SlackMessageType.CREW_CREATE, alarmResponse);
    }

    public void sendUpdateMessage(final List<AlarmResponse> alarmResponses) throws Exception {
        if (alarmResponses.stream()
                .map(alarmResponse -> alarmResponse.getCoach().getId())
                .collect(Collectors.toSet()).size() == 2) {
            postCrewMessage(SlackMessageType.CREW_UPDATE, alarmResponses.get(UPDATE_RESPONSE));
            postCoachMessage(SlackMessageType.CREW_DELETE, alarmResponses.get(ORIGIN_RESPONSE));
            postCoachMessage(SlackMessageType.CREW_CREATE, alarmResponses.get(UPDATE_RESPONSE));
            return;
        }
        postCrewMessage(SlackMessageType.CREW_UPDATE, alarmResponses.get(UPDATE_RESPONSE));
        postCoachMessage(SlackMessageType.CREW_UPDATE, alarmResponses.get(UPDATE_RESPONSE));
    }

    public void sendDeleteMessage(final AlarmResponse alarmResponse) throws Exception {
        postCrewMessage(SlackMessageType.CREW_DELETE, alarmResponse);
        postCoachMessage(SlackMessageType.CREW_DELETE, alarmResponse);
    }

    public void sendCancelMessage(final AlarmResponse alarmResponse) throws Exception {
        postCrewMessage(SlackMessageType.COACH_CANCEL, alarmResponse);
        postCoachMessage(SlackMessageType.COACH_CANCEL, alarmResponse);
    }

    private void postCrewMessage(final SlackMessageType slackMessageType,
                                 final AlarmResponse alarmResponse) throws IOException, SlackApiException {
        final ChatPostMessageRequest crewRequest = ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCrewPreviewMessage(), alarmResponse.getCoach().getNickname()))
                .attachments(generateAttachment(slackMessageType, alarmResponse, TERNOKO_CREW_URL))
                .channel(alarmResponse.getCrew().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(crewRequest);
    }

    private void postCoachMessage(final SlackMessageType slackMessageType,
                                  final AlarmResponse alarmResponse) throws IOException, SlackApiException {
        final ChatPostMessageRequest coachRequest = ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCoachPreviewMessage(), alarmResponse.getCrew().getNickname()))
                .attachments(generateAttachment(slackMessageType, alarmResponse, TERNOKO_COACH_URL))
                .channel(alarmResponse.getCoach().getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(coachRequest);
    }

    private List<Attachment> generateAttachment(final SlackMessageType slackMessageType,
                                                final AlarmResponse alarmResponse,
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
                                                                + DATE_FORMAT.format(alarmResponse.getInterviewStartTime()))
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(":smiley: *크루*"
                                                                + System.lineSeparator()
                                                                + alarmResponse.getCrew().getNickname())
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(" ")
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(":smiley: *코치*"
                                                                + System.lineSeparator()
                                                                + alarmResponse.getCoach().getNickname())
                                                        .build())
                                ).build(),
                                ContextBlock.builder().elements(List.of(
                                                MarkdownTextObject.builder()
                                                        .text("<" + homeUrl + "|터놓고로 이동>").build(),
                                                ImageElement.builder()
                                                        .imageUrl(alarmResponse.getCoach().getImageUrl())
                                                        .altText("코치 이미지")
                                                        .build(),
                                                ImageElement.builder()
                                                        .imageUrl(alarmResponse.getCrew().getImageUrl())
                                                        .altText("크루 이미지")
                                                        .build()))
                                        .build()
                        )
                ).build());
    }
}
