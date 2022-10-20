//package com.woowacourse.ternoko.support.application;
//
//import com.woowacourse.ternoko.core.domain.interview.Interview;
//import com.woowacourse.ternoko.core.domain.interview.InterviewRepository;
//import com.woowacourse.ternoko.core.domain.interview.InterviewStatusType;
//import com.woowacourse.ternoko.support.application.response.EmailResponse;
//import com.woowacourse.ternoko.support.email.EmailGenerator;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class EmailService {
//
//    @Value("${spring.mail.username}")
//    private String from;
//
//    private final JavaMailSender javaMailSender;
//    private final InterviewRepository interviewRepository;
//
//    public EmailService(final JavaMailSender javaMailSender, final InterviewRepository interviewRepository) {
//        this.javaMailSender = javaMailSender;
//        this.interviewRepository = interviewRepository;
//    }
//
//    @Scheduled(cron = "0 59 23 * * ?")
//    public void sendEmail() {
//        final LocalDateTime localDateTimeOfNextDay = LocalDateTime.now().plusDays(1);
//        final List<Interview> interviews = interviewRepository.findAllByInterviewStartDay(
//                localDateTimeOfNextDay.getYear(),
//                localDateTimeOfNextDay.getMonthValue(),
//                localDateTimeOfNextDay.getDayOfMonth());
//
//        final List<EmailResponse> emailResponses = interviews.stream()
//                .map(interview -> EmailResponse.of(interview, from))
//                .collect(Collectors.toList());
//
//        sendEmails(emailResponses);
//        changeInterviewStatus(interviews);
//    }
//
//    private void sendEmails(final List<EmailResponse> emailResponses) {
//        for (final EmailResponse emailResponse : emailResponses) {
//            javaMailSender.send(EmailGenerator.generate(emailResponse));
//        }
//    }
//
//    private void changeInterviewStatus(final List<Interview> interviews) {
//        for (Interview interview : interviews) {
//            interview.updateStatus(InterviewStatusType.FIXED);
//        }
//        interviewRepository.saveAll(interviews);
//    }
//}
