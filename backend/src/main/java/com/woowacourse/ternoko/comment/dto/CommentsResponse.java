package com.woowacourse.ternoko.comment.dto;

import com.woowacourse.ternoko.comment.domain.Comment;
import com.woowacourse.ternoko.interview.domain.Interview;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsResponse {

    private List<CommentResponse> comments;

    public static CommentsResponse of(final List<Comment> comments, final Interview interview) {
        final List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(CommentResponse.of(interview.findMemberType(comment.getMemberId()), comment));
        }
        return new CommentsResponse(commentResponses);
    }
}
