package com.woowacourse.ternoko.comment.acceptance;

import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.acceptance.AcceptanceTest;
import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.domain.InterviewStatusType;
import com.woowacourse.ternoko.interview.dto.InterviewResponse;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
}
