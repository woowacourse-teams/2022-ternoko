package com.woowacourse.ternoko.comment.domain;

import com.woowacourse.ternoko.comment.dto.CommentRequest;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.interview.domain.Interview;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "interview_id"})})
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    private String comment;

    public Comment(Member member, Interview interview, String comment) {
        this.interview = interview;
        this.member = member;
        this.comment = comment;
    }

    public void update(CommentRequest commentRequest) {
        this.comment = commentRequest.getComment();
    }
}
