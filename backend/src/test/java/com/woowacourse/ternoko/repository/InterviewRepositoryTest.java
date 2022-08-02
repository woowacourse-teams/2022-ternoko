package com.woowacourse.ternoko.repository;

import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.member.Member;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InterviewRepositoryTest {

    @Autowired
    private InterviewRepository interviewRepository;

    @Test
    @DisplayName("해당 일시에 시작하는 interivew를 들고와야 한다.")
    void findAllByInterviewStartDay() {
        // given
        Long savedInterviewId1 = saveInterview(LocalDateTime.of(2022, 7, 29, 17, 30),
                COACH1, "애쉬");
        Long savedInterviewId2 = saveInterview(LocalDateTime.of(2022, 7, 29, 17, 30),
                COACH2, "열음");
        saveInterview(LocalDateTime.of(2022, 7, 30, 17, 30),
                COACH2, "바니");

        // when
        final List<Interview> interviews = interviewRepository.findAllByInterviewStartDay(2022, 7, 29);

        // then
        assertThat(interviews.stream())
                .map(Interview::getId)
                .contains(savedInterviewId1, savedInterviewId2);
    }

    private Long saveInterview(final LocalDateTime localDateTime,
                               final Member coach,
                               final String crewNickname) {
        final Interview interview = interviewRepository.save(new Interview(
                localDateTime,
                localDateTime.plusMinutes(30),
                coach,
                crewNickname
        ));
        return interview.getId();
    }
}