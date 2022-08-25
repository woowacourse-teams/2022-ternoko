package com.woowacourse.ternoko.availabledatetime.repository;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AvailableDateTimeRepository extends JpaRepository<AvailableDateTime, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and YEAR(a.localDateTime) = :year "
            + "and MONTH(a.localDateTime) = :month")
    void deleteAllByCoachAndYearAndMonth(final Long coachId,
                                         final int year,
                                         final int month);

    @Query("select a from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and YEAR(a.localDateTime) = :year "
            + "and MONTH(a.localDateTime) = :month "
            + "and a.availableDateTimeStatus = 'OPEN' "
            + "order by a.localDateTime")
    List<AvailableDateTime> findOpenAvailableDateTimesByCoachId(final Long coachId,
                                                                final int year,
                                                                final int month);

    @Query("select a from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and a.localDateTime = :interviewDateTime")
    Optional<AvailableDateTime> findByCoachIdAndInterviewDateTime(final Long coachId,
                                                                  final LocalDateTime interviewDateTime);

    @Query("select a from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and YEAR(a.localDateTime) = :year "
            + "and MONTH(a.localDateTime) = :month "
            + "and a.availableDateTimeStatus = 'OPEN' "
            + "or a.localDateTime = (select i.interviewStartTime from Interview i where i.id = :interviewId) "
            + "order by a.localDateTime")
    List<AvailableDateTime> findAvailableDateTimesByCoachIdAndInterviewId(final Long interviewId,
                                                                          final Long coachId,
                                                                          final int year,
                                                                          final int month);
}
