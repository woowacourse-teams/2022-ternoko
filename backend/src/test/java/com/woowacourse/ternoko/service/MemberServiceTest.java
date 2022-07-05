package com.woowacourse.ternoko.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.domain.Member;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void findCoaches() {
        // when
        final List<Member> coaches = memberService.findCoaches();

        // then
        assertThat(coaches).extracting("nickname")
                .contains("준", "브리", "토미", "네오");
    }
}
