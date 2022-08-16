package com.woowacourse.ternoko.comment.domain;

import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.interview.domain.Interview;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    private String comment;

    public Comment(final Long memberId, final Interview interview, final String comment) {
        this.memberId = memberId;
        this.interview = interview;
        this.comment = comment;
    }

    public void update(CommentRequest commentRequest) {
        this.comment = commentRequest.getComment();
    }
}
