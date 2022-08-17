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
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.interview.domain.Interview;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlackAlarm {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH시 mm분",
            Locale.KOREAN);

    private final String botToken;
    private final MethodsClientImpl slackMethodClient;

    public SlackAlarm(final MethodsClientImpl slackMethodClient, @Value("${slack.botToken}") final String botToken) {
        this.slackMethodClient = slackMethodClient;
        this.botToken = botToken;
    }

    public void sendMessage(final Interview interview, final SlackMessageType slackMessageType) throws Exception {
        postCrewMessage(slackMessageType,
                interview.getCrew(),
                interview.getCoach(),
                interview.getInterviewStartTime());
        postCoachMessage(slackMessageType,
                interview.getCrew(),
                interview.getCoach(),
                interview.getInterviewStartTime());
    }

    private void postCrewMessage(final SlackMessageType slackMessageType,
                                 final Member crew,
                                 final Member coach,
                                 final LocalDateTime interviewStartTime) throws IOException, SlackApiException {
        final ChatPostMessageRequest crewRequest = ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCrewPreviewMessage(), coach.getNickname()))
                .attachments(generateAttachment(slackMessageType, crew, coach, interviewStartTime))
                .channel(crew.getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(crewRequest);
    }

    private void postCoachMessage(final SlackMessageType slackMessageType,
                                  final Member crew,
                                  final Member coach,
                                  final LocalDateTime interviewStartTime) throws IOException, SlackApiException {
        final ChatPostMessageRequest coachRequest = ChatPostMessageRequest.builder()
                .text(String.format(slackMessageType.getCoachPreviewMessage(), crew.getNickname()))
                .attachments(generateAttachment(slackMessageType, crew, coach, interviewStartTime))
                .channel(coach.getUserId())
                .token(botToken)
                .build();
        slackMethodClient.chatPostMessage(coachRequest);
    }

    private List<Attachment> generateAttachment(final SlackMessageType slackMessageType,
                                                final Member crew,
                                                final Member coach,
                                                final LocalDateTime interviewStartTime) {
        return List.of(Attachment.builder()
                .color(slackMessageType.getColor())
                .blocks(List.of(HeaderBlock.builder()
                                        .text(PlainTextObject.builder().text(slackMessageType.getAttachmentMessage()).build())
                                        .build(),
                                DividerBlock.builder().build(),
                                SectionBlock.builder().fields(
                                        List.of(MarkdownTextObject.builder()
                                                        .text(":clock3: *면담일시*" + System.lineSeparator()
                                                                + DATE_FORMAT.format(interviewStartTime))
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(":smiley: *크루*" + System.lineSeparator() + crew.getNickname())
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(" ")
                                                        .build(),
                                                MarkdownTextObject.builder()
                                                        .text(":smiley: *코치*" + System.lineSeparator() + coach.getNickname())
                                                        .build())
                                ).build(),
                                ContextBlock.builder().elements(List.of(
                                                MarkdownTextObject.builder().text("<https://ternoko.site|터놓고로 이동>").build(),
                                                ImageElement.builder().imageUrl(coach.getImageUrl()).altText("코치 이미지").build(),
                                                ImageElement.builder().imageUrl(crew.getImageUrl()).altText("크루 이미지").build()))
                                        .build()
                        )
                ).build());
    }
}
