package com.woowacourse.ternoko.comment.presentation;

import static com.woowacourse.ternoko.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW1;
import static com.woowacourse.ternoko.fixture.MemberFixture.CREW2;
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

    @Test
    @DisplayName("면담이 완료되면 코치가 한마디를 남긴다.")
    void createCommentByCoach() throws Exception {
        // given
        final int InterviewStatusFixedInterviewId = 2;
        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews/" + InterviewStatusFixedInterviewId + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(new CommentRequest("코치가 적었어요.")))
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("면담이 완료되면 크루가 한마디를 남긴다.")
    void createCommentByCrew() throws Exception {
        // given
        final int InterviewStatusFixedInterviewId = 2;
        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews/" + InterviewStatusFixedInterviewId + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(new CommentRequest("크루가 적었어요.")))
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document());
    }


    @Test
    @DisplayName("면담이 확정되지 않은 상태에서 Crew Comment 를 남길 수 없다. ")
    void InvalidCreateEditableCommentByCrew() throws Exception {
        // given
        final int InterviewStatusEditableInterviewId = 1;
        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews/" + InterviewStatusEditableInterviewId + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(new CommentRequest("크루가 적었어요.")))
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW1)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document());
    }

    @Test
    @DisplayName("본인의 면담이 아닌  ")
    void InvalidCreateCommentByCrew() throws Exception {
        // given
        final int InterviewStatusFixedInterviewId = 2;
        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/interviews/" + InterviewStatusFixedInterviewId + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(new CommentRequest("크루가 적었어요.")))
                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document());
    }

}
