//package com.woowacourse.ternoko.api;
//
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.AUTHORIZATION;
//import static com.woowacourse.ternoko.auth.application.AuthorizationExtractor.BEARER_TYPE;
//import static com.woowacourse.ternoko.support.fixture.InterviewFixture.INTERVIEW;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
//import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW2;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.woowacourse.ternoko.core.domain.comment.Comment;
//import com.woowacourse.ternoko.core.domain.member.MemberType;
//import com.woowacourse.ternoko.core.dto.request.CommentRequest;
//import com.woowacourse.ternoko.core.dto.response.CommentsResponse;
//import com.woowacourse.ternoko.core.presentation.CommentController;
//import com.woowacourse.ternoko.support.utils.WebMVCTest;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//@WebMvcTest(CommentController.class)
//public class CommentControllerTest extends WebMVCTest {
//
//    private Long FIXED_INTERVIEW_ID = 2L;
//
//    @Test
//    @DisplayName("코치 - 코멘트를 생성한다.")
//    void createCommentByCoach() throws Exception {
//        when(commentService.create(any(), any(), any())).thenReturn(1L);
//
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(new CommentRequest("코치가 적었어요.")))
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
//                .andExpect(status().isCreated())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("크루 - 코멘트를 생성한다.")
//    void createCommentByCrew() throws Exception {
//        // given
//        when(commentService.create(any(), any(), any())).thenReturn(1L);
//
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(new CommentRequest("크루가 적었어요.")))
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2)))
//                .andExpect(status().isCreated())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("코멘트를 조회한다.")
//    void findCommentByCrew() throws Exception {
//        given(commentService.findComments(any(), any()))
//                .willReturn(CommentsResponse.of(List.of(Comment.create(1L, INTERVIEW, "테스트 코멘트", MemberType.CREW)),
//                        INTERVIEW));
//
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments")
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("크루 - 코멘트를 수정한다.")
//    void updateCommentByCrew() throws Exception {
//        doNothing().when(commentService).update(any(), any(), any(), any());
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments/" + 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(new CommentRequest("수정할게용.")))
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(CREW2)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//
//    @Test
//    @DisplayName("코치 - 코멘트를 수정한다.")
//    void updateCommentByCoach() throws Exception {
//        doNothing().when(commentService).update(any(), any(), any(), any());
//        // when, then
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/interviews/" + FIXED_INTERVIEW_ID + "/comments/" + 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(new CommentRequest("수정할게용.")))
//                        .header(AUTHORIZATION, BEARER_TYPE + jwtProvider.createToken(COACH1)))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document());
//    }
//}
