package com.woowacourse.ternoko.domain;

import static com.woowacourse.ternoko.domain.InterviewStatusType.FIX;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;

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
        interview.updateStatus(FIX);

        // then
        assertThat(interview.getInterviewStatusType()).isEqualTo(FIX);
    }
}