package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.response;

import com.woowacourse.ternoko.core.domain.comment.Comment;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.dto.response.CommentResponse;
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

    public static com.woowacourse.ternoko.core.dto.response.CommentsResponse of(final List<Comment> comments, final Interview interview) {
        final List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(CommentResponse.of(interview.findMemberType(comment.getMemberId()), comment));
        }
        return new com.woowacourse.ternoko.core.dto.response.CommentsResponse(commentResponses);
    }
}
