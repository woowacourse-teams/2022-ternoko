package com.woowacourse.ternoko.comment.repository;

import com.woowacourse.ternoko.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
