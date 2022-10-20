package com.woowacourse.ternoko.core.domain.comment;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.FIXED;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEMS1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.ternoko.common.exception.CommentInvalidException;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.InterviewStatusType;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class CommentTest {

    @DisplayName("면담 젼에 코멘트를 생성하면 예외를 반환한다.")
    @Test
    void create_comment_before_now() {
        final LocalDateTime now = LocalDateTime.now();
        final Interview interview = new Interview(new AvailableDateTime(COACH1.getId(), now, USED),
                now,
                now.plusDays(1),
                COACH1,
                CREW1,
                FORM_ITEMS1);

        assertThatThrownBy(() -> Comment.create(COACH1.getId(), interview, "테스트 코멘트", MemberType.COACH))
                .isInstanceOf(CommentInvalidException.class);
    }

    @DisplayName("면담의 상태가 코멘트를 멘트를 생성하면 예외를 반환한다.")
    @ParameterizedTest
    @EnumSource(value = InterviewStatusType.class, names = {"EDITABLE", "COMPLETED", "CANCELED"})
    void create_comment(InterviewStatusType type) {
        final LocalDateTime now = LocalDateTime.now();
        final Interview 코멘트_불가_상태_면담 = new Interview(null,
                new AvailableDateTime(COACH1.getId(), now, USED),
                now,
                now.plusDays(1),
                COACH1,
                CREW1,
                FORM_ITEMS1,
                type);
        assertThatThrownBy(() -> Comment.create(COACH1.getId(), 코멘트_불가_상태_면담, "테스트 코멘트", MemberType.COACH))
                .isInstanceOf(CommentInvalidException.class);
    }

    @DisplayName("코멘트 작성자가 아니면, 코멘트를 업데이트 할 수 없다.")
    @Test
    void update_comment_false() {
        // give
        final LocalDateTime now = LocalDateTime.now();
        final Interview 진행_완료_인터뷰 = new Interview(null,
                new AvailableDateTime(COACH1.getId(), now, USED),
                now.minusDays(5),
                now.minusDays(5).plusMinutes(30), COACH1, CREW1, FORM_ITEMS1, FIXED);
        final Comment 코멘트 = Comment.create(COACH1.getId(), 진행_완료_인터뷰, "테스트 코멘트", MemberType.COACH);

        assertThatThrownBy(() -> 코멘트.update(COACH2.getId(), 진행_완료_인터뷰.getId(), "업데이트 코멘트"))
                .isInstanceOf(CommentInvalidException.class);
    }

    @DisplayName("인터뷰 작성자가 아니면 코멘트를 업데이트 할 수 없다.")
    @Test
    void update_comment_interview_Id_false() {
        // give
        final LocalDateTime now = LocalDateTime.now();
        final Interview 진행_완료_인터뷰 = new Interview(1L,
                new AvailableDateTime(COACH1.getId(), now, USED),
                now.minusDays(5),
                now.minusDays(5).plusMinutes(30),
                COACH1, CREW1, FORM_ITEMS1, FIXED);
        final Comment 코멘트 = Comment.create(COACH1.getId(), 진행_완료_인터뷰, "테스트 코멘트", MemberType.COACH);

        assertThatThrownBy(() -> 코멘트.update(COACH2.getId(), 2L, "업데이트 코멘트"))
                .isInstanceOf(CommentInvalidException.class);
    }
}
