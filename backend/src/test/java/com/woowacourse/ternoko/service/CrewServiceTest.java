package com.woowacourse.ternoko.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.dto.CrewResponse;
import com.woowacourse.ternoko.dto.request.CrewUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CrewServiceTest {

    @Autowired
    private CrewService crewService;

    @Test
    @DisplayName("slack 회원가입 후 닉네임과 이미지를 입력받아 partUpdate 한다")
    void partUpdateCrew() {
        // given
        final Crew savedCrew = crewService.save(new Crew("톰크루즈", "sudal@gmail.com", null));
        final String nickname = "매버릭";
        final String imageUrl = "탑건2.png";

        // when
        crewService.partUpdateCrew(savedCrew.getId(), new CrewUpdateRequest(nickname, imageUrl));
        final CrewResponse updatedCrew = crewService.findCrew(savedCrew.getId());
        // then

        assertThat(updatedCrew.getImageUrl()).isEqualTo(imageUrl);
        assertThat(updatedCrew.getNickname()).isEqualTo(nickname);
    }
}
