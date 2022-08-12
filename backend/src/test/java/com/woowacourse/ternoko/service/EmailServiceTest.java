package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.fixture.InterviewFixture.FORM_ITEMS1;
import static com.woowacourse.ternoko.fixture.InterviewFixture.FORM_ITEMS2;
import static com.woowacourse.ternoko.fixture.InterviewFixture.FORM_ITEMS3;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.COACH2;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW2;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW3;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.interview.domain.FormItem;
import com.woowacourse.ternoko.interview.domain.FormItemRepository;
import com.woowacourse.ternoko.interview.domain.Interview;
import com.woowacourse.ternoko.interview.domain.InterviewRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class EmailServiceTest {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender javaMailSender;
    @Autowired
    private FormItemRepository formItemRepository;

    @Test
    @DisplayName("다음날 면담이 2개 존재할때 메일 전송은 2번 일어나야 한다.")
    void sendEmail() {
        // given
        saveInterview(LocalDateTime.now().plusDays(1), COACH1, CREW1, FORM_ITEMS1);
        saveInterview(LocalDateTime.now().plusDays(1), COACH2, CREW2, FORM_ITEMS2);
        saveInterview(LocalDateTime.now().plusDays(2), COACH2, CREW3, FORM_ITEMS3);

        // when
        emailService.sendEmail();

        // then
        verify(javaMailSender, times(2)).send(any(SimpleMailMessage.class));
    }

    private Long saveInterview(final LocalDateTime localDateTime,
                               final Coach coach,
                               final Crew crew,
                               final List<FormItem> formItems) {
        final Interview interview = interviewRepository.save(new Interview(
                localDateTime,
                localDateTime.plusMinutes(30),
                coach,
                crew
        ));
        for (FormItem formItem : formItems) {
            formItem.addInterview(interview);
            formItemRepository.save(formItem);
        }
        return interview.getId();
    }
}
