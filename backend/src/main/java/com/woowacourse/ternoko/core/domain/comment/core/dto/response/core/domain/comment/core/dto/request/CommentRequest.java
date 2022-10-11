package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private String comment;
}
