package com.woowacourse.ternoko.core.domain.comment;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByInterviewId(Long interviewId);

//    void deleteByInterviewIdAndMemberId(Long interviewId, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Comment c WHERE c.interview = :interview and c.memberId IN (:coachId, :crewId)")
    void deleteByInterviewIdAndMemberIds(@Param("interview") final Interview interview,
                                               @Param("coachId") final Long coachId,
                                               @Param("crewId") final Long crewId);
}
