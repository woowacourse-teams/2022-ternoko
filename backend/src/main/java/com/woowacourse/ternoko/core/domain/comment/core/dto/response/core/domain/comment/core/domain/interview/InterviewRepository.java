package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.core.domain.interview;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterviewRepository extends JpaRepository<com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.interview.Interview, Long> {

    @Query("SELECT i FROM Interview i WHERE i.coach.id = :coachId and i.interviewStartTime BETWEEN :start and :end")
    List<com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.interview.Interview> findAllByCoachIdAndDateRange(@Param("start") final LocalDateTime start,
                                                                                                                                     @Param("end") final LocalDateTime end,
                                                                                                                                     @Param("coachId") final Long coachId);

    List<com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.interview.Interview> findAllByCrewIdOrderByInterviewStartTime(@Param("crewId") final Long crewId);

    boolean existsByCrewIdAndInterviewStartTime(@Param("crewId") final Long crewId,
                                                @Param("start") final LocalDateTime start);

    @Query("SELECT i "
            + "FROM Interview i "
            + "WHERE YEAR(i.interviewStartTime) = :year "
            + "and MONTH(i.interviewStartTime) = :month "
            + "and DAY(i.interviewStartTime) = :day")
    List<com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.interview.Interview> findAllByInterviewStartDay(@Param("year") final int year,
                                                                                                                                   @Param("month") final int month,
                                                                                                                                   @Param("day") final int day);
}
