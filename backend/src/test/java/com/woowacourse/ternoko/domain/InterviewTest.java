package com.woowacourse.ternoko.domain;

import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.FIXED;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.ternoko.common.exception.InterviewStatusException;
import com.woowacourse.ternoko.interview.domain.Interview;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InterviewTest {

    @Test
    @DisplayName("인터뷰의 상태를 바꾼다.")
    void updateStatus() {
        // given
        final LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        final Interview interview = new Interview(
                localDateTime,
                localDateTime.plusMinutes(30),
                COACH1,
                CREW1
        );

        // when
        interview.updateStatus(FIXED);

        // then
        assertThat(interview.getInterviewStatusType()).isEqualTo(FIXED);
    }

    @Test
    @DisplayName("FIX 상태인 인터뷰의 상태를 수정할 수 없다.")
    void invalidUpdateInterview() {
        // given
        final LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        final Interview interview = new Interview(
                1L,
                localDateTime,
                localDateTime.plusMinutes(30),
                COACH1,
                CREW1,
                FIXED
        );

        // when & then
        assertThatThrownBy(() -> interview.update(new Interview(
                localDateTime,
                localDateTime.plusMinutes(30),
                COACH1,
                CREW1
        ))).isInstanceOf(InterviewStatusException.class);
    }
}
