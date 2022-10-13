//package com.woowacourse.ternoko.service;
//
//import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH2;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW2;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW3;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
//import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeRepository;
//import com.woowacourse.ternoko.core.domain.interview.Interview;
//import com.woowacourse.ternoko.core.domain.interview.InterviewRepository;
//import com.woowacourse.ternoko.core.domain.interview.formitem.Answer;
//import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
//import com.woowacourse.ternoko.core.domain.interview.formitem.Question;
//import com.woowacourse.ternoko.core.domain.member.coach.Coach;
//import com.woowacourse.ternoko.core.domain.member.crew.Crew;
//import com.woowacourse.ternoko.support.application.EmailService;
//import com.woowacourse.ternoko.support.utils.DatabaseSupporter;
//import com.woowacourse.ternoko.support.utils.ServiceTest;
//import java.time.LocalDateTime;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//@ServiceTest
//class EmailServiceTest extends DatabaseSupporter {
//
//    @Autowired
//    private AvailableDateTimeRepository availableDateTimeRepository;
//
//    @Autowired
//    private InterviewRepository interviewRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    @MockBean
//    private JavaMailSender javaMailSender;
//
//    @Test
//    @DisplayName("다음날 면담이 2개 존재할때 메일 전송은 2번 일어나야 한다.")
//    void sendEmail() {
//        // given
//        saveInterview(LocalDateTime.now().plusDays(1), COACH1, CREW1,
//                createFormItems());
//        saveInterview(LocalDateTime.now().plusDays(1), COACH2, CREW2,
//                createFormItems());
//        saveInterview(LocalDateTime.now().plusDays(2), COACH2, CREW3,
//                createFormItems());
//
//        // when
//        emailService.sendEmail();
//
//        // then
//        verify(javaMailSender, times(2)).send(any(SimpleMailMessage.class));
//    }
//
//    private Long saveInterview(final LocalDateTime localDateTime,
//                               final Coach coach,
//                               final Crew crew,
//                               final List<FormItem> formItems) {
//        final LocalDateTime _2022_07_29_17_30 = LocalDateTime.of(2022, 7, 29, 17, 30);
//        final AvailableDateTime availableDateTime = availableDateTimeRepository.save(
//                new AvailableDateTime(COACH1.getId(), _2022_07_29_17_30, OPEN));
//        final Interview interview = interviewRepository.save(new Interview(
//                availableDateTime,
//                localDateTime,
//                localDateTime.plusMinutes(30),
//                coach,
//                crew,
//                formItems
//        ));
//        return interview.getId();
//    }
//
//    private List<FormItem> createFormItems() {
//        return List.of(new FormItem(null, Question.from("고정질문1"), Answer.from("고정답변1")),
//                new FormItem(null, Question.from("고정질문1"), Answer.from("고정답변1")),
//                new FormItem(null, Question.from("고정질문1"), Answer.from("고정답변1")));
//    }
//}
