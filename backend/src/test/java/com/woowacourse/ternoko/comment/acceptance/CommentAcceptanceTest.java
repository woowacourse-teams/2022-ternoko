package com.woowacourse.ternoko.comment.acceptance;

import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.acceptance.AcceptanceTest;
import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.comment.dto.CommentResponse;
import com.woowacourse.ternoko.comment.dto.CommentsResponse;
import com.woowacourse.ternoko.domain.InterviewStatusType;
import com.woowacourse.ternoko.domain.MemberType;
import com.woowacourse.ternoko.interview.dto.InterviewResponse;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class CommentAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("[크루] 면담이 끝났을 때 한마디를 적을 수 있으며 작성 시 면담이 완료된다.")
    void createCommentByCrew() {
        // given
        final Long interviewId = 1L;
        final Header header = generateHeader(CREW1.getId());
        final ExtractableResponse<Response> response = get("/api/interviews/" + interviewId, header);
        final InterviewResponse interviewResponse = response.body().as(InterviewResponse.class);
        // when
        CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다.");
        ExtractableResponse<Response> createCommentResponse = post("/api/interviews/" + interviewId + "/comments",
                header,
                commentRequest);
        // then
        assertThat(createCommentResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        final ExtractableResponse<Response> findResponse = get("/api/interviews/" + interviewId, header);
        final InterviewResponse newInterviewResponse = findResponse.body().as(InterviewResponse.class);
        assertThat(newInterviewResponse.getStatus()).isEqualTo(InterviewStatusType.CREW_COMPLETED);
    }

    @Test
    @DisplayName("[코치] 면담이 끝났을 때 한마디를 적을 수 있으며 작성 시 면담이 완료된다.")
    void createCommentByCoach() {
        // given
        final Long interviewId = 1L;
        final Header header = generateHeader(COACH1.getId());
        final ExtractableResponse<Response> response = get("/api/interviews/" + interviewId, header);
        final InterviewResponse interviewResponse = response.body().as(InterviewResponse.class);
        // when
        CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다.");
        ExtractableResponse<Response> createCommentResponse = post("/api/interviews/" + interviewId + "/comments",
                header,
                commentRequest);
        // then
        assertThat(createCommentResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        final ExtractableResponse<Response> findResponse = get("/api/interviews/" + interviewId, header);
        final InterviewResponse newInterviewResponse = findResponse.body().as(InterviewResponse.class);
        assertThat(newInterviewResponse.getStatus()).isEqualTo(InterviewStatusType.COACH_COMPLETED);
    }

    @Test
    @DisplayName("코치 크루 모두가 한마디를 작성 시 면담이 완료된다.")
    void createCommentByAll() {
        // given
        final Long interviewId = 1L;
        final Header coachHeader = generateHeader(COACH1.getId());
        final Header crewHeader = generateHeader(CREW1.getId());
        final ExtractableResponse<Response> response = get("/api/interviews/" + interviewId, coachHeader);
        final InterviewResponse interviewResponse = response.body().as(InterviewResponse.class);
        // when
        CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다.");
        post("/api/interviews/" + interviewId + "/comments", coachHeader, commentRequest);
        ExtractableResponse<Response> createCommentResponse = post("/api/interviews/" + interviewId + "/comments",
                crewHeader,
                commentRequest);
        // then
        assertThat(createCommentResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        final ExtractableResponse<Response> findResponse = get("/api/interviews/" + interviewId, crewHeader);
        final InterviewResponse newInterviewResponse = findResponse.body().as(InterviewResponse.class);
        assertThat(newInterviewResponse.getStatus()).isEqualTo(InterviewStatusType.COMPLETE);
    }

    @Test
    @DisplayName("[코치] 코치가 면담 코멘트를 남겼을 경우, 크루/코치 모두 면담 코멘트를 남겼을 경우 조회가 가능하다.")
    void getCommentByCoach_OnlyCreateCoach() {
        // given
        final Long interviewId = 1L;
        final Header header = generateHeader(COACH1.getId());
        final ExtractableResponse<Response> response = get("/api/interviews/" + interviewId, header);
        post("/api/interviews/" + interviewId + "/comments", header, new CommentRequest("너무나도 유익한 시간이었습니다."));
        // when
        ExtractableResponse<Response> getResponse = get("/api/interviews/" + interviewId + "/comments", header);
        final CommentsResponse commentsResponse = getResponse.body().as(CommentsResponse.class);
        // then
        assertAll(
                () -> assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(commentsResponse.getComments().size()).isEqualTo(1),
                () -> assertThat(commentsResponse.getComments().get(0).getRole()).isEqualTo(MemberType.COACH),
                () -> assertThat(commentsResponse.getComments().get(0).getComment()).isEqualTo("너무나도 유익한 시간이었습니다.")
        );
    }

    @Test
    @DisplayName("[크루] 크루가 면담 코멘트를 남겼을 경우, 크루/코치 모두 면담 코멘트를 남겼을 경우 조회가 가능하다.")
    void getCommentByCrew_CreateBothMember() {
        // given
        final Long interviewId = 1L;
        final Header coachHeader = generateHeader(COACH1.getId());
        post("/api/interviews/" + interviewId + "/comments", coachHeader,
                new CommentRequest("너무 즐거웠어요. 고민이 있다면 또 면담 신청해주세요."));

        final Header crewHeader = generateHeader(CREW1.getId());
        post("/api/interviews/" + interviewId + "/comments", crewHeader, new CommentRequest("너무나도 유익한 시간이었습니다."));
        // when
        ExtractableResponse<Response> getResponse = get("/api/interviews/" + interviewId + "/comments", crewHeader);
        final CommentsResponse commentsResponse = getResponse.body().as(CommentsResponse.class);
        // then
        assertAll(
                () -> assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(commentsResponse.getComments().size()).isEqualTo(2),
                () -> assertThat(commentsResponse.getComments().stream()
                        .map(CommentResponse::getRole)
                        .collect(Collectors.toList()))
                        .containsExactly(MemberType.COACH, MemberType.CREW),
                () -> assertThat(commentsResponse.getComments().stream()
                        .map(CommentResponse::getComment)
                        .collect(Collectors.toList()))
                        .containsExactly("너무 즐거웠어요. 고민이 있다면 또 면담 신청해주세요.", "너무나도 유익한 시간이었습니다.")
        );
    }
}
