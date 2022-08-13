package com.woowacourse.ternoko.comment.presentation;

import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.comment.dto.CommentsResponse;
import com.woowacourse.ternoko.config.AuthenticationPrincipal;
import com.woowacourse.ternoko.interview.application.InterviewService;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final InterviewService interviewService;

    @PostMapping("/interviews/{interviewId}/comments")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal final Long id,
                                              @PathVariable final Long interviewId,
                                              @RequestBody final CommentRequest commentRequest) {
        interviewService.createComment(id, interviewId, commentRequest);
        return ResponseEntity.created(URI.create("/api/interview/" + interviewId)).build();
    }

    @GetMapping("/interviews/{interviewId}/comments")
    public ResponseEntity<CommentsResponse> getComments(@AuthenticationPrincipal final Long id,
                                                        @PathVariable final Long interviewId) {
        CommentsResponse commentsResponse = interviewService.findComments(id, interviewId);
        return ResponseEntity.ok(commentsResponse);
    }
}
