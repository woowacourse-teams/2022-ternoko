package com.woowacourse.ternoko.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.ternoko.common.exception.InvalidMemberNicknameException;
import com.woowacourse.ternoko.core.application.CrewService;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import com.woowacourse.ternoko.core.domain.member.crew.CrewRepository;
import com.woowacourse.ternoko.core.dto.request.CrewUpdateRequest;
import com.woowacourse.ternoko.core.dto.response.CrewResponse;
import com.woowacourse.ternoko.support.utils.DatabaseSupporter;
import com.woowacourse.ternoko.support.utils.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class CrewServiceTest extends DatabaseSupporter {

    @Autowired
    private CrewService crewService;

    @Autowired
    private CrewRepository crewRepository;

    @Test
    @DisplayName("slack 회원가입 후 닉네임과 이미지를 입력받아 partUpdate 한다")
    void partUpdateCrew() {
        // given
        final Crew savedCrew = crewService.save(new Crew("톰크루즈", null, "sudal@gmail.com", "U123456789", "기본이미지"));
        final String nickname = "매버릭";
        final String imageUrl = "탑건2.png";

        // when
        crewService.updateCrew(savedCrew.getId(), new CrewUpdateRequest(nickname, imageUrl));
        final CrewResponse updatedCrew = crewService.findCrew(savedCrew.getId());

        // then
        assertAll(
                () -> assertThat(updatedCrew.getImageUrl()).isEqualTo(imageUrl),
                () -> assertThat(updatedCrew.getNickname()).isEqualTo(nickname)
        );

        crewRepository.deleteById(savedCrew.getId());
    }

    @Test
    @DisplayName("slack 회원가입 후 닉네임 변경 시 본인의 닉네임으로 변경 가능하다.")
    void partUpdateCrewSameNickName() {
        // given
        final Crew savedCrew = crewService.save(new Crew("엔젤앤지", "데빌앤지", "sudal@gmail.com", "U123456789", "기본이미지"));
        final String nickname = "데빌앤지";
        final String imageUrl = "탑건2.png";

        // when
        crewService.updateCrew(savedCrew.getId(), new CrewUpdateRequest(nickname, imageUrl));
        final CrewResponse updatedCrew = crewService.findCrew(savedCrew.getId());

        // then
        assertAll(
                () -> assertThat(updatedCrew.getImageUrl()).isEqualTo(imageUrl),
                () -> assertThat(updatedCrew.getNickname()).isEqualTo(nickname)
        );

        crewRepository.deleteById(savedCrew.getId());
    }

    @Test
    @DisplayName("slack 회원가입 후 닉네임변경 시 본인의 닉네임이 아니면서 이미 존재하는 회원 닉네임이면 예외를 반환한다.")
    void partUpdateCrewException() {
        // given
        final Crew savedCrew = crewService.save(new Crew("톰크루즈", null, "sudal@gmail.com", "U123456789", "기본이미지"));
        final String existNickname = "앤지";
        final String imageUrl = "탑건2.png";

        // when, then
        assertThatThrownBy(() ->  crewService.updateCrew(savedCrew.getId(), new CrewUpdateRequest(existNickname, imageUrl)))
                .isInstanceOf(InvalidMemberNicknameException.class);

        crewRepository.deleteById(savedCrew.getId());
    }
}
