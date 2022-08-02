package com.woowacourse.ternoko.util;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailSender {

    private JavaMailSender mailSender;

    public void send(final EmailSenderDto emailSenderDto) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailSenderDto.getFrom());
        message.setTo(emailSenderDto.getTo());
        message.setSubject(emailSenderDto.getSubject());
        message.setText(emailSenderDto.getText());

        mailSender.send(message);
        System.out.println(message);
    }

    public void send(final List<EmailSenderDto> emailSenderDtos) {
        for (EmailSenderDto emailSenderDto : emailSenderDtos) {
            send(emailSenderDto);
        }
    }
}
