package com.woowacourse.ternoko.core.domain.comment.core.domain.interview;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("SELECT i FROM Interview i WHERE i.coach.id = :coachId and i.interviewStartTime BETWEEN :start and :end")
    List<Interview> findAllByCoachIdAndDateRange(@Param("start") final LocalDateTime start,
                                                 @Param("end") final LocalDateTime end,
                                                 @Param("coachId") final Long coachId);

    List<Interview> findAllByCrewIdOrderByInterviewStartTime(@Param("crewId") final Long crewId);

    boolean existsByCrewIdAndInterviewStartTime(@Param("crewId") final Long crewId,
                                                @Param("start") final LocalDateTime start);

    @Query("SELECT i "
            + "FROM Interview i "
            + "WHERE YEAR(i.interviewStartTime) = :year "
            + "and MONTH(i.interviewStartTime) = :month "
            + "and DAY(i.interviewStartTime) = :day")
    List<Interview> findAllByInterviewStartDay(@Param("year") final int year,
                                               @Param("month") final int month,
                                               @Param("day") final int day);
}
