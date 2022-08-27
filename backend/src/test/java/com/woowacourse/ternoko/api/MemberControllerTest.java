package com.woowacourse.ternoko.api;

import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.BEARER_TYPE;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH3;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.ternoko.controller.MemberController;
import com.woowacourse.ternoko.dto.CoachResponse;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.NicknameResponse;
import com.woowacourse.ternoko.service.CoachService;
import com.woowacourse.ternoko.service.MemberService;
import com.woowacourse.ternoko.support.utils.WebMVCTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends WebMVCTest {

    @MockBean
    private CoachService coachService;
    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("크루 - 코치 목록을 조회한다.")
    void findCoaches() throws Exception {
        given(coachService.findCoaches())
                .willReturn(new CoachesResponse(List.of(CoachResponse.from(COACH1))));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/coaches")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("[코치/크루] - 닉네임 중복 검사를 한다. ")
    void checkUniqueNickname() throws Exception {
        given(memberService.hasNickname(anyString()))
                .willReturn(new NicknameResponse(false));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/login/check")
                        .queryParam("nickname", COACH3.getNickname()))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
