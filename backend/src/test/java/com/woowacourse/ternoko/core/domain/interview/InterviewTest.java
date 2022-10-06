package com.woowacourse.ternoko.core.domain.interview;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.DELETED;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.CANCELED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.COACH_COMPLETED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.COMPLETED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.CREW_COMPLETED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.EDITABLE;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.FIXED;
import static com.woowacourse.ternoko.core.domain.member.MemberType.COACH;
import static com.woowacourse.ternoko.core.domain.member.MemberType.CREW;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEMS1;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEMS2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.ternoko.common.exception.InterviewStatusException;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class InterviewTest {

    @Test
    @DisplayName("인터뷰를 수정한다.")
    void update() {
        // given
        final Interview interview = getInterview(EDITABLE);
        final AvailableDateTime originAvailableDatetime = interview.getAvailableDateTime();

        // when
        final LocalDateTime nextDay = LocalDateTime.now().plusDays(1);
        final AvailableDateTime availableDateTime = new AvailableDateTime(COACH2.getId(), nextDay.plusDays(1), USED);
        final Interview updateInterview = new Interview(
                1L,
                availableDateTime,
                nextDay,
                nextDay.plusMinutes(30),
                COACH2,
                CREW1,
                FORM_ITEMS2,
                FIXED);
        interview.update(updateInterview);

        // then
        assertAll(
                () -> assertThat(interview.getAvailableDateTime().getId()).isEqualTo(availableDateTime.getId()),
                () -> assertThat(interview.getInterviewStartTime()).isEqualTo(nextDay),
                () -> assertThat(interview.getCoach().getId()).isEqualTo(COACH2.getId()),
                () -> assertThat(interview.getCrew().getId()).isEqualTo(CREW1.getId()),
                () -> assertThat(interview.getFormItems().stream()
                        .map(FormItem::getAnswer)
                        .collect(Collectors.toList()))
                        .isEqualTo(FORM_ITEMS2.stream()
                                .map(FormItem::getAnswer)
                                .collect(Collectors.toList())),
                () -> assertThat(interview.getInterviewStatusType()).isEqualTo(FIXED),
                () -> assertThat(originAvailableDatetime.getAvailableDateTimeStatus()).isEqualTo(OPEN),
                () -> assertThat(availableDateTime.getAvailableDateTimeStatus()).isEqualTo(USED)
        );
    }

    @Test
    @DisplayName("취소된 인터뷰를 수정하면 사용하던 시간은 DELETED로 두고 새로운 시간은 USED로 갖는다.")
    void updateWithCanceledInterview() {
        // given
        final AvailableDateTime originAvailableDatetime = new AvailableDateTime(1L, COACH1.getId(),
                LocalDateTime.now().plusDays(1), DELETED);
        final Interview interview = getInterview(originAvailableDatetime, CANCELED);

        // when
        final LocalDateTime nextDay = LocalDateTime.now().plusDays(1);
        final AvailableDateTime availableDateTime = new AvailableDateTime(COACH2.getId(), nextDay.plusDays(1), USED);
        final Interview updateInterview = new Interview(
                1L,
                availableDateTime,
                nextDay,
                nextDay.plusMinutes(30),
                COACH2,
                CREW1,
                FORM_ITEMS2,
                FIXED);
        interview.update(updateInterview);

        // then
        assertAll(
                () -> assertThat(originAvailableDatetime.getAvailableDateTimeStatus()).isEqualTo(DELETED),
                () -> assertThat(availableDateTime.getAvailableDateTimeStatus()).isEqualTo(USED)
        );
    }

    @Test
    @DisplayName("같은 면담가능 시간으로 인터뷰를 수정한다.")
    void updateWithSameAvailableDateTime() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        final AvailableDateTime originAvailableDatetime = interview.getAvailableDateTime();
        final LocalDateTime nextDay = LocalDateTime.now().plusDays(1);
        final AvailableDateTime availableDateTime = new AvailableDateTime(COACH2.getId(), nextDay.plusDays(1), USED);
        final Interview updateInterview = new Interview(
                1L,
                availableDateTime,
                nextDay,
                nextDay.plusMinutes(30),
                COACH2,
                CREW1,
                FORM_ITEMS2,
                FIXED);
        interview.update(updateInterview);

        // then
        assertAll(
                () -> assertThat(originAvailableDatetime.getAvailableDateTimeStatus()).isEqualTo(OPEN),
                () -> assertThat(availableDateTime.getAvailableDateTimeStatus()).isEqualTo(USED)
        );
    }

    @Test
    @DisplayName("인터뷰에서 크루를 수정할 수 없다.")
    void updateCrew() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        final Interview updateInterview = getInterview(CREW2,
                new AvailableDateTime(2L,
                        COACH1.getId(),
                        LocalDateTime.now().plusDays(1),
                        USED));

        // then
        assertThatThrownBy(() -> interview.update(updateInterview))
                .isInstanceOf(InvalidInterviewMemberException.class);
    }

    @ParameterizedTest
    @EnumSource(value = InterviewStatusType.class, names = {"COMMENT", "FIXED", "COACH_COMPLETED", "CREW_COMPLETED",
            "COMPLETED"})
    @DisplayName("COMMENT, FIX, COACH_COMPLETED, CREW_COMPLETED, COMPLETED 상태인 인터뷰의 상태를 수정할 수 없다.")
    void invalidUpdateInterview(InterviewStatusType type) {
        // given
        final Interview interview = getInterview(type);

        // when
        final Interview updateInterview = getInterview(FIXED);

        // then
        assertThatThrownBy(() -> interview.update(updateInterview))
                .isInstanceOf(InterviewStatusException.class);
    }

    @ParameterizedTest
    @EnumSource(value = InterviewStatusType.class, names = {"EDITABLE", "CANCELED"})
    @DisplayName("EDITABLE, CANCELED 상태에서는 수정이 가능하다.")
    void canInterviewStatus(InterviewStatusType type) {
        // given
        final Interview interview = getInterview(type);

        // when
        final Interview updateInterview = getInterview(FIXED);

        // then
        assertThatNoException().isThrownBy(() -> interview.update(updateInterview));
    }

    @Test
    @DisplayName("인터뷰를 취소한다.")
    void cancel() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        interview.cancel();

        // then
        assertAll(
                () -> assertThat(interview.getInterviewStatusType()).isEqualTo(CANCELED),
                () -> assertThat(interview.getAvailableDateTime().getAvailableDateTimeStatus()).isEqualTo(DELETED)
        );
    }

    @Test
    @DisplayName("완료된 면담에 크루가 코멘트를 달았을 때, CREW_COMPLETED 상태가 된다.")
    void change_interview_status_complete_add_crew_comment() {
        // given
        final Interview interview = getInterview(FIXED);

        // when
        interview.complete(CREW);
        // then
        assertThat(interview.getInterviewStatusType()).isEqualTo(CREW_COMPLETED);
    }

    @Test
    @DisplayName("완료된 면담에 코치가 코멘트를 달았을 때, COACH_COMPLETED 상태가 된다.")
    void change_interview_status_complete_add_coach_comment() {
        // given
        final Interview interview = getInterview(FIXED);

        // when
        interview.complete(COACH);

        // then
        assertThat(interview.getInterviewStatusType()).isEqualTo(COACH_COMPLETED);
    }

    @Test
    @DisplayName("완료된 면담에 크루가 먼저 코멘트를 달고 코치가 코멘트를 달았을 때, COMPLETED 상태가 된다.")
    void change_interview_status_complete_add_all_comment_crew_first() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        interview.complete(CREW);
        interview.complete(COACH);

        // then
        assertThat(interview.getInterviewStatusType()).isEqualTo(COMPLETED);
    }

    @Test
    @DisplayName("완료된 면담에 코치가 먼저 코멘트를 달고 크루가 코멘트를 달았을 때, COMPLETED 상태가 된다.")
    void change_interview_status_complete_add_all_comment_coach_first() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        interview.complete(COACH);
        interview.complete(CREW);

        // then
        assertThat(interview.getInterviewStatusType()).isEqualTo(COMPLETED);
    }

    // TODO: 인터뷰의 책임이 아님
    @Test
    @DisplayName("면담의 코치 아이디와 일치할 경우, Coach memberType 을 반환한다.")
    void findMemberType_Coach() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        final MemberType memberType = interview.findMemberType(COACH1.getId());

        // then
        assertThat(memberType).isEqualTo(COACH);
    }

    // TODO: 인터뷰의 책임이 아님
    @Test
    @DisplayName("면담의 크루 아이디와 일치할 경우, Crew memberType 을 반환한다.")
    void findMemberType_Crew() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        final MemberType memberType = interview.findMemberType(CREW1.getId());

        // then
        assertThat(memberType).isEqualTo(CREW);
    }

    @DisplayName("member Id 가 면담에 있는 크루, 코치와 일치 하지 않는다면 예외가 발생한다.")
    @Test
    void findMemberType_false() {
        // given
        final Interview interview = getInterview(EDITABLE);
        // when & then
        assertThatThrownBy(() -> interview.findMemberType(CREW2.getId()))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @ParameterizedTest
    @EnumSource(value = InterviewStatusType.class, names = {"EDITABLE", "CANCELED", "FIXED", "COMMENT"})
    @DisplayName("코멘트를 찾을 수 없는 면담인지 확인한다.")
    void canCreateCommentBy(InterviewStatusType type) {
        final Interview interview = getInterview(type);
        assertFalse(interview.canFindCommentBy());
    }

    @ParameterizedTest
    @EnumSource(value = InterviewStatusType.class, names = {"COACH_COMPLETED", "CREW_COMPLETED", "COMPLETED"})
    @DisplayName("코멘트를 찾을 수 있는 면담인지 확인한다.")
    void canCreateCommentBy_false(InterviewStatusType type) {
        final Interview interview = getInterview(type);
        assertTrue(interview.canFindCommentBy());
    }

    @DisplayName("면담 작성자가 맞는지 확인한다.")
    @Test
    void isCreatedBy() {
        // given
        final Interview interview = getInterview(CREW1);

        //when & then
        assertTrue(interview.isCreatedBy(5L));
    }

    private Interview getInterview(final Crew crew) {
        final LocalDateTime now = LocalDateTime.now();
        return new Interview(
                1L,
                new AvailableDateTime(COACH1.getId(), now.plusDays(1), USED),
                now,
                now.plusMinutes(30),
                COACH1,
                crew,
                FORM_ITEMS1,
                EDITABLE);
    }

    private Interview getInterview(final Crew crew, final AvailableDateTime availableDateTime) {
        final LocalDateTime now = LocalDateTime.now();
        return new Interview(
                1L,
                availableDateTime,
                now,
                now.plusMinutes(30),
                COACH1,
                crew,
                FORM_ITEMS1,
                EDITABLE);
    }

    private Interview getInterview(final InterviewStatusType statusType) {
        final LocalDateTime now = LocalDateTime.now();
        return new Interview(
                1L,
                new AvailableDateTime(1L, COACH1.getId(), now.plusDays(1), USED),
                now,
                now.plusMinutes(30),
                COACH1,
                CREW1,
                FORM_ITEMS1,
                statusType);
    }

    private Interview getInterview(final AvailableDateTime availableDateTime,
                                   final InterviewStatusType statusType) {
        final LocalDateTime now = LocalDateTime.now();
        return new Interview(
                1L,
                availableDateTime,
                now,
                now.plusMinutes(30),
                COACH1,
                CREW1,
                FORM_ITEMS1,
                statusType);
    }
}

