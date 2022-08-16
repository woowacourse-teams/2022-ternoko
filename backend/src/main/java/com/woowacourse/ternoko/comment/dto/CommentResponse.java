package com.woowacourse.ternoko.comment.dto;

import com.woowacourse.ternoko.comment.domain.Comment;
import com.woowacourse.ternoko.login.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private MemberType role;
    private Long commentId;
    private String comment;

    public static CommentResponse of(MemberType memberType, Comment comment) {
        return new CommentResponse(memberType, comment.getId(), comment.getComment());
    }
}
