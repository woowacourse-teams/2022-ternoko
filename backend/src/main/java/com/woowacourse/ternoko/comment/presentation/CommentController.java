package com.woowacourse.ternoko.comment.presentation;

import com.woowacourse.ternoko.comment.application.CommentService;
import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.comment.dto.CommentsResponse;
import com.woowacourse.ternoko.login.domain.AuthenticationPrincipal;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/interviews/{interviewId}/comments")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal final Long id,
                                              @PathVariable final Long interviewId,
                                              @RequestBody final CommentRequest commentRequest) {
        Long commentId = commentService.create(id, interviewId, commentRequest);
        return ResponseEntity.created(URI.create("/api/interviews/" + interviewId + "/comments/" + commentId)).build();
    }

    @GetMapping("/interviews/{interviewId}/comments")
    public ResponseEntity<CommentsResponse> getComments(@AuthenticationPrincipal final Long id,
                                                        @PathVariable final Long interviewId) {
        CommentsResponse commentsResponse = commentService.findComments(id, interviewId);
        return ResponseEntity.ok(commentsResponse);
    }

    @PutMapping("/interviews/{interviewId}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal final Long id,
                                              @PathVariable final Long interviewId,
                                              @PathVariable final Long commentId,
                                              @RequestBody final CommentRequest commentRequest) {
        commentService.update(id, interviewId, commentId, commentRequest);
        return ResponseEntity.ok().build();
    }

}
