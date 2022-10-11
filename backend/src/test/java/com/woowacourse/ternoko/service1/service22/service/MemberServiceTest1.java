package com.woowacourse.ternoko.service1.service22.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.ternoko.core.application.MemberService;
import com.woowacourse.ternoko.support.utils.DatabaseSupporter;
import com.woowacourse.ternoko.support.utils.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class MemberServiceTest1 extends DatabaseSupporter {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 가입시, 이름 중복 검사")
    void checkUniqueNickname() {
        final String duplicateNickname = "토미";

        assertTrue(memberService.hasNickname(duplicateNickname).isExists());
    }
}
