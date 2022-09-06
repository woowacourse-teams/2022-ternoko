package com.woowacourse.ternoko.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.core.application.CrewService;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import com.woowacourse.ternoko.core.dto.response.CrewResponse;
import com.woowacourse.ternoko.core.dto.request.CrewUpdateRequest;
import com.woowacourse.ternoko.support.utils.DatabaseSupporter;
import com.woowacourse.ternoko.support.utils.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class CrewServiceTest extends DatabaseSupporter {

    @Autowired
    private CrewService crewService;

    @Test
    @DisplayName("slack 회원가입 후 닉네임과 이미지를 입력받아 partUpdate 한다")
    void partUpdateCrew() {
        // given
        final Crew savedCrew = crewService.save(new Crew("톰크루즈", null, "sudal@gmail.com", "U123456789", "기본이미지"));
        final String nickname = "매버릭";
        final String imageUrl = "탑건2.png";

        // when
        crewService.partUpdateCrew(savedCrew.getId(), new CrewUpdateRequest(nickname, imageUrl));
        final CrewResponse updatedCrew = crewService.findCrew(savedCrew.getId());
        // then

        assertAll(
                () -> assertThat(updatedCrew.getImageUrl()).isEqualTo(imageUrl),
                () -> assertThat(updatedCrew.getNickname()).isEqualTo(nickname)
        );
    }
}
