package com.woowacourse.ternoko.service;

import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.dto.EmailDto;
import com.woowacourse.ternoko.repository.InterviewRepository;
import com.woowacourse.ternoko.util.EmailSender;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class EmailService {

    private EmailSender emailSender;
    private InterviewRepository interviewRepository;

    @Scheduled(cron = "0 59 23 * * ?")
    public void sendEmail(){
        final LocalDateTime localDateTimeOfNextDay = LocalDateTime.now().plusDays(1);
        final List<Interview> interviews = interviewRepository.findAllByInterviewStartDay(
                localDateTimeOfNextDay.getYear(),
                localDateTimeOfNextDay.getMonthValue(),
                localDateTimeOfNextDay.getDayOfMonth());

        final List<EmailDto> emailDtos = EmailDto.from(interviews);
        emailSender.send(emailDtos.stream()
                .map(EmailDto::toEmailDto)
                .collect(Collectors.toList()));
    }
}
