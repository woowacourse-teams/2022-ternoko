package com.woowacourse.ternoko.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.woowacourse.ternoko.dto.EmailDto;
import com.woowacourse.ternoko.dto.FormItemDto;
import com.woowacourse.ternoko.support.EmailSender;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Test
    void mailSend() {
        // given
        final FormItemDto formItemDto = FormItemDto.formItemBuilder()
                .question("요즘 고민되는 점")
                .answer("없어용")
                .build();

        final EmailDto emailDto = EmailDto.emailDtoBuilder()
                .coachNickname("포비")
                .coachEmail("test@email.com")
                .crewNickname("수달")
                .interviewStartTime(LocalDateTime.of(2022, 8, 2, 12, 0))
                .formItems(List.of(formItemDto))
                .build();

        // when
        mailSender.send(emailDto);

        // then
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}
