package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment;

import com.woowacourse.ternoko.core.domain.comment.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByInterviewId(Long interviewId);
}
