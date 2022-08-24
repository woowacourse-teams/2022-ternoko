package com.woowacourse.ternoko.repository;

import static com.woowacourse.ternoko.fixture.InterviewFixture.FORM_ITEMS1;
import static com.woowacourse.ternoko.fixture.InterviewFixture.FORM_ITEMS2;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW2;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.interview.domain.Interview;
import com.woowacourse.ternoko.interview.domain.InterviewRepository;
import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class InterviewRepositoryTest {

    @Autowired
    private InterviewRepository interviewRepository;

    @Test
    @DisplayName("해당 일시에 시작하는 interivew를 들고와야 한다.")
    void findAllByInterviewStartDay() {
        // given
        final Long savedInterviewId1 = saveInterview(LocalDateTime.of(2022, 7, 29, 17, 30),
                COACH1, CREW1, FORM_ITEMS1);
        final Long savedInterviewId2 = saveInterview(LocalDateTime.of(2022, 7, 29, 16, 30),
                COACH2, CREW2, FORM_ITEMS2);
        // when
        final List<Interview> interviews = interviewRepository.findAllByInterviewStartDay(2022, 7, 29);

        // then
        assertThat(interviews.stream())
                .map(Interview::getId)
                .contains(savedInterviewId1, savedInterviewId2);
    }

    private Long saveInterview(final LocalDateTime localDateTime,
                               final Coach coach,
                               final Crew crew, List<FormItem> formItems) {

        final Interview interview = interviewRepository.save(new Interview(
                localDateTime,
                localDateTime.plusMinutes(30),
                coach,
                crew,
                formItems
        ));
        return interview.getId();
    }
}
