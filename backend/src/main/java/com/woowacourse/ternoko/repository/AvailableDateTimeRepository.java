package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.AvailableDateTime;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AvailableDateTimeRepository extends JpaRepository<AvailableDateTime, Long> {

    @Query("delete from AvailableDateTime a where a.coach.id = :coachId and YEAR(a.localDateTime) = :year and MONTH(a.localDateTime) = :month")
    void deleteAllByCoachAndYearAndMonth(final Long coachId, final int year, final int month);

    @Query("select a from AvailableDateTime a where a.coach.id = :coachId and YEAR(a.localDateTime) = :year and MONTH(a.localDateTime) = :month")
    List<AvailableDateTime> findAvailableDateTimesByCoachId(final Long coachId, final int year, final int month);

    @Query("select a from AvailableDateTime a where a.coach.id = :coachId and a.localDateTime = :interviewDateTime")
    Optional<AvailableDateTime> findByCoachIdAndInterviewDateTime(Long coachId, LocalDateTime interviewDateTime);
}
