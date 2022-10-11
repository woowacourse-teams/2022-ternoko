package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.response;

import com.woowacourse.ternoko.core.domain.comment.Comment;
import com.woowacourse.ternoko.core.domain.member.MemberType;
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

    public static com.woowacourse.ternoko.core.dto.response.CommentResponse of(MemberType memberType, Comment comment) {
        return new com.woowacourse.ternoko.core.dto.response.CommentResponse(memberType, comment.getId(), comment.getComment());
    }
}
