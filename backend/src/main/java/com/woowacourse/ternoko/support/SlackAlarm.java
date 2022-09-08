package com.woowacourse.ternoko.support;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.model.Attachment;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.ImageElement;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SlackAlarm {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH시 mm분",
            Locale.KOREAN);
    private static final String TERNOKO_CREW_URL = "https://ternoko.site/";
    private static final String TERNOKO_COACH_URL = "https://ternoko.site/coach/home";
    private static final int ORIGIN_RESPONSE = 0;
    private static final int UPDATE_RESPONSE = 1;

    private final String botToken;
    private final String url;
    private final MethodsClientImpl slackMethodClient;

    public SlackAlarm(final MethodsClientImpl slackMethodClient,
                      @Value("${slack.botToken}") final String botToken,
                      @Value("${slack.url}") final String url) {
        this.slackMethodClient = slackMethodClient;
        this.botToken = botToken;
        this.url = url;
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
        SlackPostMessageRequest request = SlackPostMessageRequest.builder()
                .channel(interview.getCrew().getUserId())
                .text(String.format(slackMessageType.getCrewPreviewMessage(), interview.getCoach().getNickname()))
                .build();
        WebClient client = WebClient.create(url);
        client.post()
                .uri("/api/send")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .header("Authorization", botToken)
                .body(BodyInserters.fromValue(request));
    }

    private void postCoachMessage(final SlackMessageType slackMessageType,
                                  final Interview interview) throws IOException, SlackApiException {
        SlackPostMessageRequest request = SlackPostMessageRequest.builder()
                .channel(interview.getCoach().getUserId())
                .text(String.format(slackMessageType.getCoachPreviewMessage(), interview.getCrew().getNickname()))
                .build();
        WebClient client = WebClient.create("http://43.200.246.162:8080");
        client.post()
                .uri("/api/send")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .header("Authorization", botToken)
                .body(BodyInserters.fromValue(request));
    }

    private List<Attachment> generateAttachment(final SlackMessageType slackMessageType,
                                                final Interview interview,
                                                final String homeUrl) {
        return List.of(getAttachment(slackMessageType, interview, homeUrl));
    }

    private Attachment getAttachment(SlackMessageType slackMessageType, Interview interview, String homeUrl) {
        return Attachment.builder()
                .color(slackMessageType.getColor())
                .blocks(getBlocks(slackMessageType, interview, homeUrl)
                ).build();
    }

    @NotNull
    private List<LayoutBlock> getBlocks(SlackMessageType slackMessageType, Interview interview, String homeUrl) {
        return List.of(
                getHeaderBlock(slackMessageType),
                getDividerBlock(),
                getSectionBlock(interview),
                getContextBlock(interview, homeUrl));
    }

    private HeaderBlock getHeaderBlock(SlackMessageType slackMessageType) {
        return HeaderBlock.builder()
                .text(PlainTextObject.builder().text(slackMessageType.getAttachmentMessage()).build())
                .build();
    }

    private DividerBlock getDividerBlock() {
        return DividerBlock.builder().build();
    }

    private SectionBlock getSectionBlock(Interview interview) {
        return SectionBlock.builder().fields(
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
        ).build();
    }

    private ContextBlock getContextBlock(Interview interview, String homeUrl) {
        return ContextBlock.builder().elements(List.of(
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
                .build();
    }
}
