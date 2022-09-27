package com.woowacourse.ternoko.core.domain.availabledatetime;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.USED;

import com.woowacourse.ternoko.support.time.TimeMachine;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coach_id", nullable = false)
    private Long coachId;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Enumerated(EnumType.STRING)
    private AvailableDateTimeStatus availableDateTimeStatus;

    public AvailableDateTime(final Long coachId,
                             final LocalDateTime localDateTime,
                             final AvailableDateTimeStatus availableDateTimeStatus) {
        this.coachId = coachId;
        this.localDateTime = localDateTime;
        this.availableDateTimeStatus = availableDateTimeStatus;
    }

    public void changeStatus(final AvailableDateTimeStatus status) {
        this.availableDateTimeStatus = status;
    }

    public boolean isPast() {
        final LocalDate nowDay = TimeMachine.dateOfNow();
        final LocalDate targetDay = localDateTime.toLocalDate();
        return targetDay.isBefore(nowDay);
    }

    public boolean isToday() {
        final LocalDate nowDay = TimeMachine.dateOfNow();
        final LocalDate targetDay = localDateTime.toLocalDate();
        return targetDay.isEqual(nowDay);
    }

    public boolean isUsed() {
        return availableDateTimeStatus.matchType(USED);
    }
}
