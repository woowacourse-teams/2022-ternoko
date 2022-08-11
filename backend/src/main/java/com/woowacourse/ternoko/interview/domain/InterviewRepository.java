package com.woowacourse.ternoko.interview.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("SELECT i FROM Interview i WHERE i.coach.id = :coachId and i.interviewStartTime BETWEEN :start and :end")
    List<Interview> findAllByCoachIdAndDateRange(final LocalDateTime start, final LocalDateTime end,
                                                 final Long coachId);

    List<Interview> findAllByCrewIdOrderByInterviewStartTime(final Long crewId);

    boolean existsByCrewIdAndInterviewStartTime(final Long crewId, final LocalDateTime start);
}
