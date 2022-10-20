package com.woowacourse.ternoko.core.domain.availabledatetime;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AvailableDateTimeRepository extends JpaRepository<AvailableDateTime, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and YEAR(a.localDateTime) = :year "
            + "and MONTH(a.localDateTime) = :month "
            + "and a.availableDateTimeStatus = :status")
    void deleteAllByCoachAndYearAndMonthAndStatus(@Param("coachId") final Long coachId,
                                                  @Param("year") final int year,
                                                  @Param("month") final int month,
                                                  @Param("status") final AvailableDateTimeStatus status);

    List<AvailableDateTime> findAllByCoachId(final Long coachId);

    @Query("select a from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and YEAR(a.localDateTime) = :year "
            + "and MONTH(a.localDateTime) = :month "
            + "and a.availableDateTimeStatus = 'OPEN'"
            + "order by a.localDateTime")
    List<AvailableDateTime> findOpenAvailableDateTimesByCoachId(@Param("coachId") final Long coachId,
                                                                @Param("year") final int year,
                                                                @Param("month") final int month);

    @Query("select a from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and YEAR(a.localDateTime) = :year "
            + "and MONTH(a.localDateTime) = :month "
            + "and (a.availableDateTimeStatus = 'OPEN' or a.availableDateTimeStatus = 'USED') "
            + "order by a.localDateTime")
    List<AvailableDateTime> findOpenAndUsedAvailableDateTimesByCoachId(@Param("coachId") final Long coachId,
                                                                @Param("year") final int year,
                                                                @Param("month") final int month);

    @Query("select count(a.id) from AvailableDateTime a "
            + "where a.coachId = :coachId "
            + "and a.localDateTime between :startDateTime and :endDateTime ")
    Long countByCoachId(@Param("coachId") final Long coachId,
                        @Param("startDateTime") final LocalDateTime startDateTime,
                        @Param("endDateTime") final LocalDateTime endDateTime);
}
