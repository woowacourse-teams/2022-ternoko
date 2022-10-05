package com.woowacourse.ternoko.domain.interview;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEMS1;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEMS2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW2;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeRepository;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.InterviewRepository;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import com.woowacourse.ternoko.core.domain.member.MemberRepository;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class InterviewRepositoryTest {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private AvailableDateTimeRepository availableDateTimeRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Coach coach1;
    private Coach coach2;
    private Crew crew1;
    private Crew crew2;

    @BeforeEach
    void setUp() {
        coach1 = memberRepository.save(COACH1);
        coach2 = memberRepository.save(COACH2);
        crew1 = memberRepository.save(CREW1);
        crew2 = memberRepository.save(CREW2);
    }

    @AfterEach
    void cleanUp() {
        memberRepository.delete(COACH1);
        memberRepository.delete(COACH2);
        memberRepository.delete(CREW1);
        memberRepository.delete(CREW2);
    }

    @Test
    @DisplayName("해당 일시에 시작하는 interivew를 들고와야 한다.")
    void findAllByInterviewStartDay() {
        // given
        final LocalDateTime _2022_07_29_17_30 = LocalDateTime.of(2022, 7, 29, 17, 30);
        final LocalDateTime _2022_07_29_16_30 = LocalDateTime.of(2022, 7, 29, 16, 30);
        final AvailableDateTime availableDateTime1 = availableDateTimeRepository.save(
                new AvailableDateTime(COACH1.getId(), _2022_07_29_17_30, OPEN));
        final AvailableDateTime availableDateTime2 = availableDateTimeRepository.save(
                new AvailableDateTime(COACH1.getId(), _2022_07_29_16_30, OPEN));

        final Long savedInterviewId1 = saveInterview(_2022_07_29_17_30,
                availableDateTime1,
                coach1, crew1, FORM_ITEMS1);
        final Long savedInterviewId2 = saveInterview(_2022_07_29_16_30,
                availableDateTime2,
                coach2, crew2, FORM_ITEMS2);
        // when
        final List<Interview> interviews = interviewRepository.findAllByInterviewStartDay(2022, 7, 29);

        // then
        assertThat(interviews.stream())
                .map(Interview::getId)
                .contains(savedInterviewId1, savedInterviewId2);
    }

    private Long saveInterview(final LocalDateTime localDateTime,
                               final AvailableDateTime availableDateTime,
                               final Coach coach,
                               final Crew crew, List<FormItem> formItems) {
        final Interview interview = interviewRepository.save(new Interview(
                availableDateTime,
                localDateTime,
                localDateTime.plusMinutes(30),
                coach,
                crew,
                formItems
        ));
        return interview.getId();
    }
}
