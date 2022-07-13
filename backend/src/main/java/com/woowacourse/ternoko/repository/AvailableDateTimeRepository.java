package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.AvailableDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AvailableDateTimeRepository extends JpaRepository<AvailableDateTime, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from AvailableDateTime a where a.coach.id = :coachId and YEAR(a.localDateTime) = :year and MONTH(a.localDateTime) = :month")
    void deleteAllByCoachAndYearAndMonth(Long coachId, int year, int month);

    List<AvailableDateTime> findAvailableDateTimesByCoachId(Long coachId);
}
