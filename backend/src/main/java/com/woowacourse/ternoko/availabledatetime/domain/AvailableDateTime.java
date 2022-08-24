package com.woowacourse.ternoko.availabledatetime.domain;

import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.USED;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_AVAILABLE_DATE_TIME;

import com.woowacourse.ternoko.interview.exception.InvalidInterviewDateException;
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

    public AvailableDateTime(final Long coachId, final LocalDateTime localDateTime,
                             final AvailableDateTimeStatus availableDateTimeStatus) {
        this.coachId = coachId;
        this.localDateTime = localDateTime;
        this.availableDateTimeStatus = availableDateTimeStatus;
    }

    public void changeStatus(final AvailableDateTimeStatus status) {
        this.availableDateTimeStatus = status;
    }

    public void validateAvailableDateTime() {
        if (isUsed()) {
            throw new InvalidInterviewDateException(INVALID_AVAILABLE_DATE_TIME);
        }
    }

    private boolean isUsed() {
        return availableDateTimeStatus.matchType(USED);
    }
}
