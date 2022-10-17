package com.woowacourse.ternoko.core.domain.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByInterviewId(Long interviewId);

    void deleteByInterviewIdAndMemberId(Long interviewId, Long memberId);
}
