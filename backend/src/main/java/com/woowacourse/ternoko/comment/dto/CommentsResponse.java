package com.woowacourse.ternoko.comment.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsResponse {

    private List<CommentResponse> comments;

    public static CommentsResponse from(List<CommentResponse> commentResponses) {
        return new CommentsResponse(commentResponses);
    }
}
