package com.woowacourse.ternoko.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailSender mailSender;

    @MockBean
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.username}")
    private String toEmail;

    @Test
    void mailSend() {
        EmailSenderDto emailSenderDto = new EmailSenderDto(
                fromEmail,
                toEmail,
                "title",
                "content"
        );
        mailSender.send(emailSenderDto);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}
