package com.woowacourse.ternoko.comment.application;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COMMENT_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INTERVIEW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_COMMENT_INTERVIEW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_MEMBER_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_STATUS_CREATE_COMMENT;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.ternoko.core.application.CommentService;
import com.woowacourse.ternoko.core.presentation.request.CommentRequest;
import com.woowacourse.ternoko.core.application.response.CommentResponse;
import com.woowacourse.ternoko.core.application.response.CommentsResponse;
import com.woowacourse.ternoko.common.exception.exception.CommentNotFoundException;
import com.woowacourse.ternoko.common.exception.exception.InvalidCommentInterviewIdException;
import com.woowacourse.ternoko.common.exception.exception.InvalidStatusCreateCommentException;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import com.woowacourse.ternoko.core.application.InterviewService;
import com.woowacourse.ternoko.core.domain.interview.InterviewStatusType;
import com.woowacourse.ternoko.core.application.response.InterviewResponse;
import com.woowacourse.ternoko.common.exception.InterviewNotFoundException;
import com.woowacourse.ternoko.support.utils.DatabaseSupporter;
import com.woowacourse.ternoko.support.utils.ServiceTest;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class CommentServiceTest extends DatabaseSupporter {

    private static final Long FIXED_INTERVIEW_ID = 2L;
    private static final long NOT_FOUNT_INTERVIEW_ID = -1L;
    private static final long EDITABLE_INTERVIEW_ID = 3L;
    private static final long NOT_FOUND_COMMENT_ID = -1L;

    @Autowired
    private CommentService commentService;

    @Autowired
    private InterviewService interviewService;

    @Test
    @DisplayName("[크루] 면담 종료 시 면담에 대한 한마디를 작성하면 면담이 완료된다.")
    void createCommentByCrew() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");

        // when
        Long commentId = commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // then
        InterviewResponse interviewResponseById = interviewService.findInterviewResponseById(FIXED_INTERVIEW_ID);
        assertThat(commentId).isNotNull();
        assertThat(interviewResponseById.getStatus()).isEqualTo(InterviewStatusType.CREW_COMPLETED);
    }

    @Test
    @DisplayName("[코치] 면담 종료 시 면담에 대한 한마디를 작성하면 면담이 완료된다.")
    void createCommentByCoach() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");

        // when
        Long commentId = commentService.create(COACH1.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // then
        InterviewResponse interviewResponseById = interviewService.findInterviewResponseById(FIXED_INTERVIEW_ID);
        assertThat(commentId).isNotNull();
        assertThat(interviewResponseById.getStatus()).isEqualTo(InterviewStatusType.COACH_COMPLETED);
    }

    @Test
    @DisplayName("[크루] 코치가 한마디 작성 후 크루가 작성한다면 면담 상태는 COMPLETE가 된다.")
    void createComment_Complete_ByCrew() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        commentService.create(COACH1.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // when
        Long commentId = commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // then
        InterviewResponse interviewResponseById = interviewService.findInterviewResponseById(FIXED_INTERVIEW_ID);
        assertThat(commentId).isNotNull();
        assertThat(interviewResponseById.getStatus()).isEqualTo(InterviewStatusType.COMPLETE);
    }

    @Test
    @DisplayName("[코치] 크루가 한마디 작성 후 코치가 작성한다면 면담 상태는 COMPLETE가 된다.")
    void createComment_Complete_ByCoach() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // when
        Long commentId = commentService.create(COACH1.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // then
        InterviewResponse interviewResponseById = interviewService.findInterviewResponseById(FIXED_INTERVIEW_ID);
        assertThat(commentId).isNotNull();
        assertThat(interviewResponseById.getStatus()).isEqualTo(InterviewStatusType.COMPLETE);
    }

    @Test
    @DisplayName("면담에 관련되지 않은 회원이 코멘트를 작성하면 예외를 반환한다.")
    void createComment_InvalidMember() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");

        // when & then
        assertThatThrownBy(() -> commentService.create(COACH2.getId(), FIXED_INTERVIEW_ID, commentRequest))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage(INVALID_INTERVIEW_MEMBER_ID.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 면담에 코멘트를 작성하면 예외를 반환한다.")
    void createComment_InvalidInterview() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");

        // when & then
        assertThatThrownBy(() -> commentService.create(COACH1.getId(), NOT_FOUNT_INTERVIEW_ID, commentRequest))
                .isInstanceOf(InterviewNotFoundException.class)
                .hasMessage(NOT_FOUNT_INTERVIEW_ID + INTERVIEW_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("[크루] 2번 이상 코멘트를 남기면 예외를 발생한다.")
    void createCommentByCrew_InvalidStatus_Twice() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");

        // when
        commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, commentRequest);
        assertThatThrownBy(() -> commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, commentRequest))
                .isInstanceOf(InvalidStatusCreateCommentException.class)
                .hasMessage(INVALID_STATUS_CREATE_COMMENT.getMessage());
    }

    @Test
    @DisplayName("[코치] COMMENT, CREW_COMPLETE 이외의 상태에서 코멘트를 남기면 예외를 발생한다.")
    void createCommentByCoach_InvalidStatus_Twice() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");

        // when & then
        commentService.create(COACH1.getId(), FIXED_INTERVIEW_ID, commentRequest);
        assertThatThrownBy(() -> commentService.create(COACH1.getId(), FIXED_INTERVIEW_ID, commentRequest))
                .isInstanceOf(InvalidStatusCreateCommentException.class)
                .hasMessage(INVALID_STATUS_CREATE_COMMENT.getMessage());
    }

    @Test
    @DisplayName("[코치] COMMENT, CREW_COMPLETE 이외의 상태에서 코멘트를 남기면 예외를 발생한다.")
    void createCommentByCoach_InvalidStatus_OtherStatus() {
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        assertThatThrownBy(() -> commentService.create(COACH1.getId(), EDITABLE_INTERVIEW_ID, commentRequest))
                .isInstanceOf(InvalidStatusCreateCommentException.class)
                .hasMessage(INVALID_STATUS_CREATE_COMMENT.getMessage());
    }

    @Test
    @DisplayName("[크루] CREW_COMMENT, COMPLETE 상태에서 조회가 가능하다.")
    void findComment_Complete_ByCrew() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // when
        final CommentsResponse commentsResponse = commentService.findComments(CREW2.getId(), FIXED_INTERVIEW_ID);

        // then
        assertThat(commentsResponse.getComments().size()).isEqualTo(1);
        assertThat(commentsResponse.getComments().get(0).getComment()).isEqualTo("너무나도 유익한 시간이었습니다. 감사합니다.");
        assertThat(commentsResponse.getComments().get(0).getRole()).isEqualTo(MemberType.CREW);
    }

    @Test
    @DisplayName("[코치] CREW_COMMENT, COMPLETE 상태에서 조회가 가능하다.")
    void findComment_Complete_ByCoach() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        commentService.create(COACH1.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // when
        final CommentsResponse commentsResponse = commentService.findComments(COACH1.getId(), FIXED_INTERVIEW_ID);

        // then
        assertThat(commentsResponse.getComments().size()).isEqualTo(1);
        assertThat(commentsResponse.getComments().get(0).getComment()).isEqualTo("너무나도 유익한 시간이었습니다. 감사합니다.");
        assertThat(commentsResponse.getComments().get(0).getRole()).isEqualTo(MemberType.COACH);
    }

    @Test
    @DisplayName("[크루/코치] 크루,코치 모두 한마디를 작성한 경우(COMPLETE 상태) 조회가 가능하다.")
    void findComment_Complete_ByBothMember() {
        // given
        final CommentRequest coachCommentRequest = new CommentRequest("재밌는 시간 고마워요. 고민이 해결되기를 바랄게요.");
        final CommentRequest crewCommentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        commentService.create(COACH1.getId(), FIXED_INTERVIEW_ID, coachCommentRequest);
        commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, crewCommentRequest);

        // when
        final CommentsResponse commentsResponse = commentService.findComments(COACH1.getId(), FIXED_INTERVIEW_ID);

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
    @DisplayName("존재하지 않는 면담에 코멘트를 조회하면 예외를 반환한다.")
    void findComment_InvalidInterview() {
        // given
        final Long interviewId = -1L;
        // when & then
        assertThatThrownBy(() -> commentService.findComments(COACH1.getId(), interviewId))
                .isInstanceOf(InterviewNotFoundException.class)
                .hasMessage(interviewId + INTERVIEW_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("면담에 관련되지 않은 회원이 조회하면 예외를 반환한다.")
    void findComment_InvalidInterviewMember() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // when & then
        assertThatThrownBy(() -> commentService.findComments(CREW1.getId(), FIXED_INTERVIEW_ID))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage(INVALID_INTERVIEW_MEMBER_ID.getMessage());
    }

    @Test
    @DisplayName("[크루] 면담에 본인이 작성한 코멘트 수정이 가능하다.")
    void updateComment() {
        // given
        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long commentId = commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, commentRequest);

        // when
        final CommentRequest updateCommentRequest = new CommentRequest("이 말을 빼먹었어요~");
        commentService.update(CREW2.getId(), FIXED_INTERVIEW_ID, commentId, updateCommentRequest);
        final CommentsResponse commentsResponse = commentService.findComments(CREW2.getId(), FIXED_INTERVIEW_ID);

        // then
        assertThat(commentsResponse.getComments().size()).isEqualTo(1);
        assertThat(commentsResponse.getComments().get(0).getComment()).isEqualTo("이 말을 빼먹었어요~");
        assertThat(commentsResponse.getComments().get(0).getRole()).isEqualTo(MemberType.CREW);
    }

    @Test
    @DisplayName("[크루] 존재하지 않는 코멘트에 수정 요청을 하면 예외를 반환한다.")
    void updateComment_NotFoundComment() {
        // given

        final CommentRequest commentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long commentId = commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, commentRequest);
        // when & then
        final CommentRequest updateCommentRequest = new CommentRequest("이 말을 빼먹었어요~");
        assertThatThrownBy(() -> commentService.update(CREW2.getId(), FIXED_INTERVIEW_ID, 10L, updateCommentRequest))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage(10L + COMMENT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("[크루] 수정하려는 코멘트가 자신이 작성한 코멘트가 아닐 경우 예외를 반환한다.")
    void updateComment_InvalidMember() {
        // given
        final Long firstInterviewId = 1L;
        final Long secondInterviewId = 2L;
        final CommentRequest firstRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long firstCommentId = commentService.create(CREW1.getId(), firstInterviewId, firstRequest);

        final CommentRequest secondRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long secondCommentId = commentService.create(CREW2.getId(), secondInterviewId, secondRequest);
        // when & then
        final CommentRequest updateCommentRequest = new CommentRequest("이 말을 빼먹었어요~");
        assertThatThrownBy(() -> commentService.update(CREW2.getId(), firstInterviewId, secondCommentId,
                updateCommentRequest))
                .isInstanceOf(InvalidCommentInterviewIdException.class)
                .hasMessage(INVALID_COMMENT_INTERVIEW_ID.getMessage());
    }

    @Test
    @DisplayName("[크루] 수정하려는 코멘트가 자신이 작성한 코멘트가 아닐 경우 예외를 반환한다.")
    void updateComment_InvalidInterview() {
        // given
        final Long firstInterviewId = 1L;
        final Long secondInterviewId = 2L;
        final CommentRequest firstRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long firstCommentId = commentService.create(CREW1.getId(), firstInterviewId, firstRequest);

        final CommentRequest secondRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        Long secondCommentId = commentService.create(CREW2.getId(), secondInterviewId, secondRequest);
        // when & then
        final CommentRequest updateCommentRequest = new CommentRequest("이 말을 빼먹었어요~");
        assertThatThrownBy(() -> commentService.update(CREW2.getId(), firstInterviewId, secondCommentId,
                updateCommentRequest))
                .isInstanceOf(InvalidCommentInterviewIdException.class)
                .hasMessage(INVALID_COMMENT_INTERVIEW_ID.getMessage());
    }

    @Test
    @DisplayName("[크루] 존재하지 않는 코멘트에 수정 요청을 하면 예외를 반환한다.")
    void updateComment_NotFound() {
        // given
        final CommentRequest crewCommentRequest = new CommentRequest("너무나도 유익한 시간이었습니다. 감사합니다.");
        commentService.create(CREW2.getId(), FIXED_INTERVIEW_ID, crewCommentRequest);

        // when & then
        final CommentRequest updateCommentRequest = new CommentRequest("이 말을 빼먹었어요~");
        assertThatThrownBy(
                () -> commentService.update(CREW2.getId(), FIXED_INTERVIEW_ID, NOT_FOUND_COMMENT_ID,
                        updateCommentRequest))
                .isInstanceOf(CommentNotFoundException.class);
    }
}
