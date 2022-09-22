package com.woowacourse.ternoko.support;

import com.slack.api.methods.request.chat.ChatPostMessageRequest;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SlackAlarm {

    private static final String TERNOKO_CREW_URL = "https://ternoko.site/";
    private static final String TERNOKO_COACH_URL = "https://ternoko.site/coach/home";
    private static final String DATE_MESSAGE = "yyyy년 MM월 dd일(E) HH시 mm분";
    public static final String ALARM_BOT_URI = "/api/send";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_MESSAGE, Locale.KOREAN);

    private final String botToken;
    private final WebClient webClient;

    public SlackAlarm(@Value("${slack.botToken}") final String botToken, @Value("${slack.url}") final String url) {
        this.webClient = WebClient.create(url);
        this.botToken = botToken;
    }

    public void sendCreateMessage(final Interview interview) {
        postCrewMessage(SlackMessageType.CREW_CREATE, interview);
        postCoachMessage(SlackMessageType.CREW_CREATE, interview);
    }

    public void sendUpdateMessage(final Interview origin, final Interview update) {
        if (!origin.getCoach().getNickname().equals(update.getCoach().getNickname())) {
            postCrewMessage(SlackMessageType.CREW_UPDATE, update);
            postCoachMessage(SlackMessageType.CREW_DELETE, origin);
            postCoachMessage(SlackMessageType.CREW_CREATE, update);
            return;
        }
        postCrewMessage(SlackMessageType.CREW_UPDATE, update);
        postCoachMessage(SlackMessageType.CREW_UPDATE, update);
    }

    public void sendDeleteMessage(final Interview interview) {
        postCrewMessage(SlackMessageType.CREW_DELETE, interview);
        postCoachMessage(SlackMessageType.CREW_DELETE, interview);
    }

    public void sendCancelMessage(final Interview interview) {
        postCrewMessage(SlackMessageType.COACH_CANCEL, interview);
        postCoachMessage(SlackMessageType.COACH_CANCEL, interview);
    }

    private void postCrewMessage(final SlackMessageType slackMessageType, final Interview interview) {
        final ChatPostMessageRequest crewRequest = ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCrewPreviewMessage(), interview.getCoach().getNickname()))
                .attachments(generateAttachment(slackMessageType, interview, TERNOKO_CREW_URL))
                .channel(interview.getCrew().getUserId())
                .token(botToken)
                .build();

        postWebClient(crewRequest);
    }

    private void postCoachMessage(final SlackMessageType slackMessageType, final Interview interview) {
        final ChatPostMessageRequest coachRequest = ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCoachPreviewMessage(), interview.getCrew().getNickname()))
                .attachments(generateAttachment(slackMessageType, interview, TERNOKO_COACH_URL))
                .channel(interview.getCoach().getUserId())
                .token(botToken)
                .build();
        postWebClient(coachRequest);
    }

    private void postWebClient(ChatPostMessageRequest request) {
        webClient.post()
                .uri(ALARM_BOT_URI)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .header(AUTHORIZATION_HEADER, botToken)
                .bodyValue(request)
                .exchange().block();
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
    private List<LayoutBlock> getBlocks(final SlackMessageType slackMessageType, final Interview interview, final String homeUrl) {
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
