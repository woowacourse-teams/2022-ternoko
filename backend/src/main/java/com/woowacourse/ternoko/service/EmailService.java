package com.woowacourse.ternoko.service;

import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.InterviewStatusType;
import com.woowacourse.ternoko.dto.EmailDto;
import com.woowacourse.ternoko.repository.InterviewRepository;
import com.woowacourse.ternoko.support.EmailSender;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;

    private final EmailSender emailSender;
    private final InterviewRepository interviewRepository;

    public EmailService(final EmailSender emailSender, final InterviewRepository interviewRepository) {
        this.emailSender = emailSender;
        this.interviewRepository = interviewRepository;
    }

    @Scheduled(cron = "0 59 23 * * ?")
    public void sendEmail() {
        final LocalDateTime localDateTimeOfNextDay = LocalDateTime.now().plusDays(1);
        final List<Interview> interviews = interviewRepository.findAllByInterviewStartDay(
                localDateTimeOfNextDay.getYear(),
                localDateTimeOfNextDay.getMonthValue(),
                localDateTimeOfNextDay.getDayOfMonth());

        final List<EmailDto> emailDtos = interviews.stream()
                .map(interview -> EmailDto.of(interview, from))
                .collect(Collectors.toList());

        sendEmails(emailDtos);
        changeInterviewStatus(interviews);
    }

    private void sendEmails(final List<EmailDto> emailDtos) {
        for (final EmailDto emailDto : emailDtos) {
            emailSender.send(emailDto);
        }
    }

    private void changeInterviewStatus(final List<Interview> interviews) {
        for (Interview interview : interviews) {
            interview.updateStatus(InterviewStatusType.FIX);
        }
        interviewRepository.saveAll(interviews);
    }
}
