package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.Interview;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("SELECT i FROM Interview i WHERE i.coach.id = :coachId and i.interviewStartTime BETWEEN :start and :end")
    List<Interview> findAllByCoachIdAndDateRange(final LocalDateTime start,
                                                 final LocalDateTime end,
                                                 final Long coachId);

    @Query("SELECT i "
            + "FROM Interview i "
            + "WHERE YEAR(i.interviewStartTime) = :year "
            + "and MONTH(i.interviewStartTime) = :month "
            + "and DAY(i.interviewStartTime) = :day")
    List<Interview> findAllByInterviewStartDay(final int year,
                                               final int month,
                                               final int day);
}
