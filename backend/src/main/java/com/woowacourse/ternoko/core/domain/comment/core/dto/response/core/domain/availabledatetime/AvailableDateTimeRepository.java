package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.availabledatetime;

import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AvailableDateTimeRepository extends JpaRepository<AvailableDateTime, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and YEAR(a.localDateTime) = :year "
            + "and MONTH(a.localDateTime) = :month")
    void deleteAllByCoachAndYearAndMonth(@Param("coachId") final Long coachId,
                                         @Param("year") final int year,
                                         @Param("month") final int month);

    @Query("select a from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and YEAR(a.localDateTime) = :year "
            + "and MONTH(a.localDateTime) = :month "
            + "and a.availableDateTimeStatus = 'OPEN' "
            + "order by a.localDateTime")
    List<AvailableDateTime> findOpenAvailableDateTimesByCoachId(@Param("coachId") final Long coachId,
                                                                @Param("year") final int year,
                                                                @Param("month") final int month);

    @Query("select a from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and a.localDateTime = :interviewDateTime")
    Optional<AvailableDateTime> findByCoachIdAndInterviewDateTime(@Param("coachId") final Long coachId,
                                                                  @Param("interviewDateTime") final LocalDateTime interviewDateTime);

    @Query("select a from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and YEAR(a.localDateTime) = :year "
            + "and MONTH(a.localDateTime) = :month "
            + "and a.availableDateTimeStatus = 'OPEN' "
            + "or (a.localDateTime = (select i.interviewStartTime from Interview i where i.id = :interviewId) and a.coachId = :coachId) "
            + "order by a.localDateTime")
    List<AvailableDateTime> findByCoachIdAndYearAndMonthAndOpenOrInterviewStartTime(
            @Param("interviewId") final Long interviewId,
            @Param("coachId") final Long coachId,
            @Param("year") final int year,
            @Param("month") final int month);

    @Query("select count(a.id) from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and a.localDateTime between :startDateTime and :endDateTime ")
    Long countByCoachId(@Param("coachId") final Long coachId,
            @Param("startDateTime") final LocalDateTime startDateTime,
            @Param("endDateTime")  final LocalDateTime endDateTime);
}
