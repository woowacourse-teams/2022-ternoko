package com.woowacourse.ternoko.comment.presentation;

import static com.woowacourse.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.support.fixture.MemberFixture.CREW2;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.AUTHORIZATION;
import static com.woowacourse.ternoko.login.presentation.AuthorizationExtractor.BEARER_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.ternoko.api.ControllerTest;
import com.woowacourse.ternoko.comment.dto.CommentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CommentControllerTest extends ControllerTest {

    private static final Long FIXED_INTERVIEW_ID = 2L;

    @Test
    @DisplayName("코치 - 코멘트를 생성한다.")
    void createCommentByCoach() throws Exception {
        // given
        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(new CommentRequest("코치가 적었어요.")))
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루 - 코멘트를 생성한다.")
    void createCommentByCrew() throws Exception {
        // given
        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(new CommentRequest("크루가 적었어요.")))
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코멘트를 조회한다.")
    void findCommentByCrew() throws Exception {
        // given
        createComment(FIXED_INTERVIEW_ID, new CommentRequest("코치의 코멘트 입니다."), COACH1);
        createComment(FIXED_INTERVIEW_ID, new CommentRequest("크루의 코멘트 입니다."), CREW2);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments")
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("크루 - 코멘트를 수정한다.")
    void updateCommentByCrew() throws Exception {
        // given
        createComment(FIXED_INTERVIEW_ID, new CommentRequest("코치의 코멘트 입니다."), COACH1);
        final Long commentId = createComment(FIXED_INTERVIEW_ID, new CommentRequest("크루의 코멘트 입니다."), CREW2);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments/" + commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(new CommentRequest("수정할게용.")))
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("코치 - 코멘트를 수정한다.")
    void updateCommentByCoach() throws Exception {
        // given
        final Long commentId = createComment(FIXED_INTERVIEW_ID, new CommentRequest("코치의 코멘트 입니다."), COACH1);
        createComment(FIXED_INTERVIEW_ID, new CommentRequest("크루의 코멘트 입니다."), CREW2);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments/" + commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(new CommentRequest("수정할게용.")))
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
