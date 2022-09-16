package com.woowacourse.ternoko.support.alarm;

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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class SlackMessageGenerator {

    private static final String TERNOKO_CREW_URL = "https://ternoko.site/";
    private static final String TERNOKO_COACH_URL = "https://ternoko.site/coach/home";
    private static final String DATE_MESSAGE = "yyyy년 MM월 dd일(E) HH시 mm분";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_MESSAGE, Locale.KOREAN);

    public static ChatPostMessageRequest getCoachMessageRequest(final SlackMessageType slackMessageType,
                                                                final Interview interview,
                                                                final String botToken) {
        System.out.println("Request 생성자 : "+interview.getCoach().getUserId());
        return ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCoachPreviewMessage(), interview.getCrew().getNickname()))
                .attachments(generateAttachment(slackMessageType, interview, TERNOKO_COACH_URL))
                .channel(interview.getCoach().getUserId())
                .token(botToken)
                .build();
    }

    public static ChatPostMessageRequest getCrewMessageRequest(final SlackMessageType slackMessageType,
                                                               final Interview interview,
                                                               final String botToken) {
        System.out.println("Request 생성자 : "+interview.getCrew().getUserId());
        return ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCrewPreviewMessage(), interview.getCoach().getNickname()))
                .attachments(generateAttachment(slackMessageType, interview, TERNOKO_CREW_URL))
                .channel(interview.getCrew().getUserId())
                .token(botToken)
                .build();
    }

    private static List<Attachment> generateAttachment(final SlackMessageType slackMessageType,
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
                                                        .text(":clock3: *면담일시*" +
                                                                System.lineSeparator() +
                                                                DATE_FORMAT.format(interview.getInterviewStartTime()))
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(":smiley: *크루*" +
                                                                System.lineSeparator() +
                                                                interview.getCrew().getNickname())
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(" ")
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(":smiley: *코치*" +
                                                                System.lineSeparator() +
                                                                interview.getCoach().getNickname())
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
