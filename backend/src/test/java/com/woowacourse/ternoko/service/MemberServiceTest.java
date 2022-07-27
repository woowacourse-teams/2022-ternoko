package com.woowacourse.ternoko.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 가입시, 이름 중복 검사")
    void checkUniqueNickname() {
        final String duplicateNickname = "토미";

        assertTrue(memberService.hasNickname(duplicateNickname).isExists());
    }
}
