package com.woowacourse.ternoko.comment.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COMMENT_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_INTERVIEW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_MEMBER_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_MEMBER_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_FIND_COMMENT;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.comment.dto.CommentResponse;
import com.woowacourse.ternoko.comment.dto.CommentsResponse;
import com.woowacourse.ternoko.comment.exception.CommentNotFoundException;
import com.woowacourse.ternoko.comment.exception.InvalidCommentInterviewIdException;
import com.woowacourse.ternoko.comment.exception.InvalidCommentMemberIdException;
import com.woowacourse.ternoko.comment.exception.InvalidStatusFindCommentException;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.domain.MemberType;
import com.woowacourse.ternoko.interview.application.InterviewService;
import com.woowacourse.ternoko.interview.exception.InterviewNotFoundException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CommentServiceTest {

    @Autowired
    private InterviewService interviewService;

    @Test
    @DisplayName("[크루] CREW_COMMENT, COMPLETE 상태에서 조회가 가능하다.")
    void findComment_Complete_ByCrew() {
        // given
        final Long interviewId = 1L;
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        interviewService.createComment(CREW1.getId(), interviewId, commentRequest);
        // when
        final CommentsResponse commentsResponse = interviewService.findComments(CREW1.getId(), interviewId);
        // then
        assertThat(commentsResponse.getComments().size()).isEqualTo(1);
        assertThat(commentsResponse.getComments().get(0).getComment()).isEqualTo("너무나도 유익한 시간이었습니다. 감사합니다.");
        assertThat(commentsResponse.getComments().get(0).getRole()).isEqualTo(MemberType.CREW);
    }

    @Test
    @DisplayName("[코치] CREW_COMMENT, COMPLETE 상태에서 조회가 가능하다.")
    void findComment_Complete_ByCoach() {
        // given
        final Long interviewId = 1L;
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        interviewService.createComment(COACH1.getId(), interviewId, commentRequest);
        // when
        final CommentsResponse commentsResponse = interviewService.findComments(COACH1.getId(), interviewId);
        // then
        assertThat(commentsResponse.getComments().size()).isEqualTo(1);
        assertThat(commentsResponse.getComments().get(0).getComment()).isEqualTo("너무나도 유익한 시간이었습니다. 감사합니다.");
        assertThat(commentsResponse.getComments().get(0).getRole()).isEqualTo(MemberType.COACH);
    }

    @Test
    @DisplayName("[크루/코치] 크루,코치 모두 한마디를 작성한 경우(COMPLETE 상태) 조회가 가능하다.")
    void findComment_Complete_ByBothMember() {
        // given
        final Long interviewId = 1L;
        final CommentRequest coachCommentRequest = new CommentRequest("재밌는 시간 고마워요. 고민이 해결되기를 바랄게요.");
        final CommentRequest crewCommentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        interviewService.createComment(COACH1.getId(), interviewId, coachCommentRequest);
        interviewService.createComment(CREW1.getId(), interviewId, crewCommentRequest);
        // when
        final CommentsResponse commentsResponse = interviewService.findComments(COACH1.getId(), interviewId);
        // then
        assertThat(commentsResponse.getComments().size()).isEqualTo(2);
        assertThat(commentsResponse.getComments().stream()
                .map(CommentResponse::getComment)
                .collect(Collectors.toList()))
                .containsExactly(coachCommentRequest.getComment(), crewCommentRequest.getComment());
        assertThat(commentsResponse.getComments().stream()
                .map(CommentResponse::getRole)
                .collect(Collectors.toList()))
                .containsExactly(MemberType.COACH, MemberType.CREW);
    }

    @Test
    @DisplayName("[크루] 코치만 한마디를 작성한 상태에서 조회를 하면 예외를 반환한다.")
    void findComment_OnlyCoachComment_WhenFindByCrew() {
        // given
        final Long interviewId = 1L;
        final CommentRequest commentRequest = new CommentRequest("재밌는 시간 고마워요. 고민이 해결되기를 바랄게요.");
        interviewService.createComment(COACH1.getId(), interviewId, commentRequest);
        // when &  then
        assertThatThrownBy(() -> interviewService.findComments(CREW1.getId(), interviewId))
                .isInstanceOf(InvalidStatusFindCommentException.class)
                .hasMessage(INVALID_STATUS_FIND_COMMENT.getMessage());
    }

    @Test
    @DisplayName("[코치] 크루만 한마디를 작성한 상태에서 조회를 하면 예외를 반환한다.")
    void findComment_OnlyCrewComment_WhenFindByCoach() {
        // given
        final Long interviewId = 1L;
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        interviewService.createComment(CREW1.getId(), interviewId, commentRequest);
        // when &  then
        assertThatThrownBy(() -> interviewService.findComments(COACH1.getId(), interviewId))
                .isInstanceOf(InvalidStatusFindCommentException.class)
                .hasMessage(INVALID_STATUS_FIND_COMMENT.getMessage());
    }

    @Test
    @DisplayName("[코치/크루] 한마디를 작성하지 않은 상태에서 조회를 하면 예외를 반환한다.")
    void findComment_InvalidStatus_WhenFindByCoach() {
        // given
        final Long interviewId = 1L;
        // when &  then
        assertThatThrownBy(() -> interviewService.findComments(COACH1.getId(), interviewId))
                .isInstanceOf(InvalidStatusFindCommentException.class)
                .hasMessage(INVALID_STATUS_FIND_COMMENT.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 면담에 코멘트를 조회하면 예외를 반환한다.")
    void findComment_InvalidInterview() {
        // given
        final Long interviewId = 10L;
        // when & then
        assertThatThrownBy(() -> interviewService.findComments(COACH1.getId(), interviewId))
                .isInstanceOf(InterviewNotFoundException.class)
                .hasMessage(interviewId + INTERVIEW_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("면담에 관련되지 않은 회원이 조회하면 예외를 반환한다.")
    void findComment_InvalidInterviewMember() {
        // given
        final Long interviewId = 1L;
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        interviewService.createComment(CREW1.getId(), interviewId, commentRequest);
        // when & then
        assertThatThrownBy(() -> interviewService.createComment(CREW2.getId(), interviewId, commentRequest))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage(INVALID_INTERVIEW_MEMBER_ID.getMessage());
    }

    @Test
    @DisplayName("[크루] 면담에 본인이 작성한 코멘트 수정이 가능하다.")
    void updateComment() {
        // given
        final Long interviewId = 1L;
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long commentId = interviewService.createComment(CREW1.getId(), interviewId, commentRequest);
        // when
        final CommentRequest updateCommentRequest = new CommentRequest("이 말을 빼먹었어요~");
        interviewService.updateComment(CREW1.getId(), interviewId, commentId, updateCommentRequest);
        final CommentsResponse commentsResponse = interviewService.findComments(CREW1.getId(), interviewId);
        // then
        assertThat(commentsResponse.getComments().size()).isEqualTo(1);
        assertThat(commentsResponse.getComments().get(0).getComment()).isEqualTo("이 말을 빼먹었어요~");
        assertThat(commentsResponse.getComments().get(0).getRole()).isEqualTo(MemberType.CREW);
    }

    @Test
    @DisplayName("[크루] 존재하지 않는 코멘트에 수정 요청을 하면 예외를 반환한다.")
    void updateComment_NotFoundComment() {
        // given
        final Long interviewId = 1L;
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long commentId = interviewService.createComment(CREW1.getId(), interviewId, commentRequest);
        // when & then
        final CommentRequest updateCommentRequest = new CommentRequest("이 말을 빼먹었어요~");
        assertThatThrownBy(() -> interviewService.updateComment(CREW1.getId(), interviewId, 10L, updateCommentRequest))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage(10L + COMMENT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("[크루] 수정하려는 코멘트가 면담에 작성된 코멘트가 경우 예외를 반환한다.")
    void updateComment_InvalidInterview() {
        // given
        final Long firstInterviewId = 1L;
        final Long secondInterviewId = 3L;
        final CommentRequest firstRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long firstCommentId = interviewService.createComment(CREW1.getId(), firstInterviewId, firstRequest);

        final CommentRequest secondRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long secondCommentId = interviewService.createComment(CREW1.getId(), secondInterviewId, secondRequest);
        // when & then
        final CommentRequest updateCommentRequest = new CommentRequest("이 말을 빼먹었어요~");
        assertThatThrownBy(() -> interviewService.updateComment(CREW1.getId(), firstInterviewId, secondCommentId,
                updateCommentRequest))
                .isInstanceOf(InvalidCommentInterviewIdException.class)
                .hasMessage(INVALID_COMMENT_INTERVIEW_ID.getMessage());
    }

    @Test
    @DisplayName("[크루] 존재하지 않는 코멘트에 수정 요청을 하면 예외를 반환한다.")
    void updateComment_InvalidMember() {
        // given
        final Long interviewId = 1L;
        final CommentRequest crewCommentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        interviewService.createComment(CREW1.getId(), interviewId, crewCommentRequest);
        final CommentRequest coachCommentRequest = new CommentRequest("즐거운 면담이었습니다. 오늘도 즐거운 하루 되세요.");
        Long coachCommentId = interviewService.createComment(COACH1.getId(), interviewId, coachCommentRequest);
        // when & then
        final CommentRequest updateCommentRequest = new CommentRequest("이 말을 빼먹었어요~");
        assertThatThrownBy(
                () -> interviewService.updateComment(CREW1.getId(), interviewId, coachCommentId, updateCommentRequest))
                .isInstanceOf(InvalidCommentMemberIdException.class)
                .hasMessage(INVALID_COMMENT_MEMBER_ID.getMessage());
    }
}
