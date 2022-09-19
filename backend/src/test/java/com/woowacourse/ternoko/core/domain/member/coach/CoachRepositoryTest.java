package com.woowacourse.ternoko.core.domain.member.coach;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.woowacourse.ternoko.core.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class CoachRepositoryTest {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private MemberRepository memberRepository;


    @DisplayName("코치의 닉네임과 이미지, 소개를  업데이트 한다.")
    @Test
    void updateNickNameAndImageUrlAndIntroduce() {
        // given
        final Coach 코치 = memberRepository.save(
                new Coach(null, "이름", "변경전", "변경전@test.com", "U9234567891", "imageUrl", "안녕하세요."));

        String nickName = "변경후";
        String imgUrl = "update img url";
        String introduce = "update introduce content";
        // when
        coachRepository.updateNickNameAndImageUrlAndIntroduce(코치.getId(), nickName, imgUrl, introduce);
        final Coach 변경_후_코치 = coachRepository.findById(코치.getId()).get();

        // then
        assertAll(
                () -> assertEquals(변경_후_코치.getNickname(), nickName),
                () -> assertEquals(변경_후_코치.getImageUrl(), imgUrl),
                () -> assertEquals(변경_후_코치.getIntroduce(), introduce)
        );

        coachRepository.delete(변경_후_코치);
    }


    @DisplayName("업데이트 될 코치의 닉네임이 고유하지 않으면 에러가 발생한다.")
    @Test
    void updateNickNameAndImageUrlAndIntroduceNotUniqueNickname() {
        // given
        final Coach 코치 = memberRepository.save(
                new Coach(null, "이름", "변경전", "변경전@test.com", "U9234567891", "imageUrl", "안녕하세요."));

        String existNickname = "수달";
        String imgUrl = "update img url";
        String introduce = "update introduce content";
        // when

        // then
        assertThatThrownBy(
                () -> coachRepository.updateNickNameAndImageUrlAndIntroduce(코치.getId(), existNickname, imgUrl,
                        introduce))
                .isInstanceOf(DataIntegrityViolationException.class);

        coachRepository.delete(코치);
    }
}
