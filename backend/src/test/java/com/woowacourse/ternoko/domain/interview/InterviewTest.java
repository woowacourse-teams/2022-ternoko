package com.woowacourse.ternoko.domain.interview;

import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.CANCELED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.COACH_COMPLETED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.COMPLETE;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.ternoko.common.exception.InterviewStatusException;
import com.woowacourse.ternoko.common.exception.InvalidInterviewCoachIdException;
import com.woowacourse.ternoko.common.exception.InvalidInterviewCrewIdException;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.InterviewStatusType;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class InterviewTest {

    @DisplayName("인터뷰 업데이트시 면담 시간 변경")
    @Test
    void updateInterviewTime() {
        // given
        final LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        final Interview interview = new Interview(
                localDateTime,
                localDateTime.plusMinutes(30),
                COACH1,
                CREW1,
                FORM_ITEMS1
        );

        final LocalDateTime updateTime = localDateTime.plusDays(2);
        final Interview updateInterview = new Interview(
                localDateTime,
                updateTime.plusMinutes(30),
                COACH1,
                CREW1,
                FORM_ITEMS1
        );

        // when
        interview.update(updateInterview);

        // then
        assertEqualsAll(interview, updateInterview);
    }

    @DisplayName("인터뷰 업데이트시 코치 변경")
    @Test
    void updateCoach() {
        // given
        final Interview interview = getInterview(EDITABLE);

        final Interview updateInterview = new Interview(
                interview.getInterviewStartTime(),
                interview.getInterviewStartTime().plusMinutes(30),
                COACH2,
                CREW1,
                FORM_ITEMS1
        );

        // when
        interview.update(updateInterview);

        // then
        assertEqualsAll(interview, updateInterview);
    }

    @DisplayName("크루 업데이트 시 예외가 발생한다.")
    @Test
    void updateCrewFalse() {
        // given
        final Interview interview = getInterview(EDITABLE);

        final Interview updateInterview = new Interview(
                interview.getInterviewStartTime(),
                interview.getInterviewStartTime().plusMinutes(30),
                COACH1,
                CREW2,
                FORM_ITEMS1
        );

        // when & then
        assertThatThrownBy(() -> interview.update(updateInterview))
                .isInstanceOf(InvalidInterviewCrewIdException.class);
    }

    @DisplayName("인터뷰 업데이트시 질답 변경")
    @Test
    void updateFormItems() {
        // given
        final Interview interview = getInterview(EDITABLE);

        final Interview updateInterview = new Interview(
                interview.getInterviewStartTime(),
                interview.getInterviewEndTime(),
                COACH1,
                CREW1,
                FORM_ITEMS2
        );

        // when
        interview.update(updateInterview);

        // then
        Assertions.assertAll(
                () -> assertThat(interview.getCoach()).isEqualTo(updateInterview.getCoach()),
                () -> assertThat(interview.getFormItems().containsAll(updateInterview.getFormItems())),
                () -> assertThat(interview.getInterviewStatusType()).isEqualTo(
                        updateInterview.getInterviewStatusType()),
                () -> assertThat(interview.getInterviewStartTime()).isEqualTo(updateInterview.getInterviewStartTime()),
                () -> assertThat(interview.getInterviewEndTime()).isEqualTo(updateInterview.getInterviewEndTime())
        );
    }

    @Test
    @DisplayName("인터뷰의 상태를 바꾼다.")
    void updateStatus() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        interview.updateStatus(FIXED);

        // then
        assertThat(interview.getInterviewStatusType()).isEqualTo(FIXED);
    }

    private void assertEqualsAll(final Interview interview, final Interview updateInterview) {
        Assertions.assertAll(
                () -> assertThat(interview.getCoach()).isEqualTo(updateInterview.getCoach()),
                () -> assertThat(interview.getCrew()).isEqualTo(updateInterview.getCrew()),
                () -> assertThat(interview.getInterviewStatusType()).isEqualTo(
                        updateInterview.getInterviewStatusType()),
                () -> assertThat(interview.getInterviewStartTime()).isEqualTo(updateInterview.getInterviewStartTime()),
                () -> assertThat(interview.getInterviewEndTime()).isEqualTo(updateInterview.getInterviewEndTime())
        );
    }

    @ParameterizedTest
    @EnumSource(value = InterviewStatusType.class, names = {"COMMENT", "FIXED", "COACH_COMPLETED", "CREW_COMPLETED",
            "COMPLETE"})
    @DisplayName("COMMENT,FIX,COACH_COMPLETED,CREW_COMPLETED,COMPLETE 상태인 인터뷰의 상태를 수정할 수 없다.")
    void invalidUpdateInterview(InterviewStatusType type) {
        // given
        final Interview interview = getInterview(type);

        final Interview updateInterview = new Interview(
                interview.getInterviewStartTime(),
                interview.getInterviewStartTime().plusMinutes(30),
                COACH1,
                CREW1,
                FORM_ITEMS1);

        // when & then
        assertThatThrownBy(() -> interview.update(updateInterview))
                .isInstanceOf(InterviewStatusException.class);
    }

    @ParameterizedTest
    @EnumSource(value = InterviewStatusType.class, names = {"EDITABLE", "CANCELED"})
    @DisplayName("EDITABLE, CANCELED 상태에서는 수정이 가능하다.")
    void canInterviewStatus(InterviewStatusType type) {
        // given
        final Interview interview = getInterview(type);

        final Interview updateInterview = new Interview(
                interview.getInterviewStartTime(),
                interview.getInterviewStartTime().plusMinutes(30),
                COACH1,
                CREW1,
                FORM_ITEMS1);

        //when & then
        assertThatNoException().isThrownBy(() -> interview.update(updateInterview));
    }

    @DisplayName("코치 본인이 인터뷰를 취소한다.")
    @Test
    void cancel() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        interview.cancel(COACH1.getId());
        // then

        assertThat(interview.getInterviewStatusType().name().equals(CANCELED.name()));
    }

    @DisplayName("다른 코치가 인터뷰를 취소시 예외가 발생한다.")
    @Test
    void cancel_false() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when & then
        assertThatThrownBy(() -> interview.cancel(COACH2.getId()))
                .isInstanceOf(InvalidInterviewCoachIdException.class);
    }

    @DisplayName("완료된 면담에 크루가 코멘트를 달았을 때, 상태변경을 해준다.")
    @Test
    void change_interview_status_complete_add_crew_comment() {
        // given
        final Interview interview = getInterview(FIXED);

        // when
        interview.complete(CREW);
        // then
        assertThat(interview.getInterviewStatusType().name().equals(CREW_COMPLETED.name()));
    }

    @DisplayName("완료된 면담에 코치가 코멘트를 달았을 때, 상태변경을 해준다.")
    @Test
    void change_interview_status_complete_add_coach_comment() {
        // given
        final Interview interview = getInterview(FIXED);

        // when
        interview.complete(COACH);
        // then
        assertThat(interview.getInterviewStatusType().name().equals(COACH_COMPLETED.name()));
    }

    private Interview getInterview(final InterviewStatusType statusType) {
        final LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        return new Interview(
                1L,
                localDateTime,
                localDateTime.plusMinutes(30),
                COACH1,
                CREW1,
                FORM_ITEMS1,
                statusType);
    }

    @DisplayName("완료된 면담에 크루와 코치 모두 코멘트를 달았을 때, 상태변경을 해준다.")
    @Test
    void change_interview_status_complete_add_all_comment() {
        // given
        final Interview interview = getInterview(COACH_COMPLETED);

        // when
        interview.complete(CREW);
        // then
        assertThat(interview.getInterviewStatusType().name().equals(COMPLETE.name()));
    }

    @DisplayName("면담의 코치 아이디와 일치할 경우, Coach memberType 을 반환한다.")
    @Test
    void findMemberType_Coach() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        final MemberType memberType = interview.findMemberType(COACH1.getId());

        // then
        assertThat(memberType.name().equals(COACH.name()));
    }

    @DisplayName("면담의 크루 아이디와 일치할 경우, Crew memberType 을 반환한다.")
    @Test
    void findMemberType_Crew() {
        // given
        final Interview interview = getInterview(EDITABLE);

        // when
        final MemberType memberType = interview.findMemberType(CREW1.getId());

        // then
        assertThat(memberType.name().equals(CREW.name()));
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
        // given
        final Interview interview = getInterview(type);

        //when & then
        assertFalse(interview.canFindCommentBy());
    }

    @ParameterizedTest
    @EnumSource(value = InterviewStatusType.class, names = {"COACH_COMPLETED", "CREW_COMPLETED",
            "COMPLETE"})
    @DisplayName("코멘트를 찾을 수 있는  면담인지 확인한다.")
    void canCreateCommentBy_false(InterviewStatusType type) {
        // given
        final Interview interview = getInterview(type);

        //when & then
        assertTrue(interview.canFindCommentBy());
    }

    @DisplayName("같은 면담인지 확인한다.")
    @Test
    void isSame() {
        // given
        final Interview interview = getInterview(EDITABLE);

        //when & then
        assertTrue(interview.isSame(interview.getId()));
    }

    @DisplayName("같은 면담이 아닌지 확인한다.")
    @Test
    void isSame_false() {
        // given
        final Interview interview = getInterview(EDITABLE);

        //when & then
        assertFalse(interview.isSame(interview.getId() + 1));
    }

    @DisplayName("면담 작성자가 맞는지 확인한다.")
    @Test
    void isCreatedBy() {
        // given
        final Interview interview = getInterview(EDITABLE);

        //when & then
        assertTrue(interview.isCreatedBy(interview.getCrew().getId()));
    }

    @DisplayName("같은 면담이 아닌지 확인한다.")
    @Test
    void isCreatedBy_false() {
        // given
        final Interview interview = getInterview(EDITABLE);

        //when & then
        assertFalse(interview.isCreatedBy(interview.getCrew().getId() + 1));
    }
}

