package com.woowacourse.ternoko.core.domain.member.crew;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.woowacourse.ternoko.core.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CrewRepositoryTest {

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private MemberRepository memberRepository;


    @DisplayName("코치의 닉네임과 이미지, 소개를  업데이트 한다.")
    @Test
    void updateNickNameAndImageUrlAndIntroduce() {
        // given
        final Crew 크루 = memberRepository.save(
                new Crew(null, "이름", "변경전", "변경전@test.com", "U9234567891", "imageUrl"));

        String nickName = "변경후";
        String imgUrl = "update img url";
        // when

        crewRepository.updateNickNameAndImageUrl(크루.getId(), nickName, imgUrl);
        final Crew 변경_후_크루 = crewRepository.findById(크루.getId()).get();

        // then
        assertAll(
                () -> assertEquals(변경_후_크루.getNickname(), nickName),
                () -> assertEquals(변경_후_크루.getImageUrl(), imgUrl)
        );

        crewRepository.delete(변경_후_크루);
    }
}
