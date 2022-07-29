package com.woowacourse.ternoko.util;

import com.woowacourse.ternoko.dto.MailDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailSender {

    private JavaMailSender mailSender;

    public void send(final MailDto mailDto) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailDto.getFrom());
        message.setTo(mailDto.getTo());
        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getText());

        mailSender.send(message);
    }
}
