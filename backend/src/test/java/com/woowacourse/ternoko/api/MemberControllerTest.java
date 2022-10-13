//package com.woowacourse.ternoko.api;
//
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.AUTHORIZATION;
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.BEARER_TYPE;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH3;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.woowacourse.ternoko.api.restdocs.RestDocsTestSupporter;
//import com.woowacourse.ternoko.core.dto.response.CoachResponse;
//import com.woowacourse.ternoko.core.dto.response.CoachesResponse;
//import com.woowacourse.ternoko.core.dto.response.NicknameResponse;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//class MemberControllerTest extends RestDocsTestSupporter {
//
//    @Test
//    @DisplayName("크루 - 코치 목록을 조회한다.")
//    void findCoaches() throws Exception {
//        given(coachService.findCoaches())
//                .willReturn(new CoachesResponse(List.of(CoachResponse.from(COACH1))));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/coaches")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("[코치/크루] - 닉네임 중복 검사를 한다. ")
//    void checkUniqueNickname() throws Exception {
//        given(memberService.hasNickname(anyString()))
//                .willReturn(new NicknameResponse(false));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/login/check")
//                        .queryParam("nickname", COACH3.getNickname()))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//}
