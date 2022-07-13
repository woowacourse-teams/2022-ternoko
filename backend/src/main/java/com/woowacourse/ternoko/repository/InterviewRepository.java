package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.Interview;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("SELECT i FROM Interview i WHERE i.coach.id = :coachId and i.interviewStartTime BETWEEN :start and :end")
    List<Interview> findAllByCoachAndDateRange(@Param(value = "start") final LocalDateTime calendarStart,
                                               @Param(value = "end") final LocalDateTime calendarEnd,
                                               @Param(value = "coachId") final Long coachId);
}
