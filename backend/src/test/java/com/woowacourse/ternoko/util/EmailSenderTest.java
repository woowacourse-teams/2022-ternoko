package com.woowacourse.ternoko.util;

import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
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

        final EmailDto emailDto = new EmailDto(
                "testFromEmail@test.com",
                COACH1.getNickname(),
                COACH1.getEmail(),
                CREW1.getNickname(),
                LocalDateTime.of(2022,8,2,0,0),
                List.of(formItemDto)
        );

        // when
        mailSender.send(emailDto);

        // then
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}
