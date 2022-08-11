package com.woowacourse.ternoko.availabledatetime.domain;

import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.OPEN;
import static com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus.USED;

import com.woowacourse.ternoko.domain.member.Coach;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Coach coach;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Enumerated(EnumType.STRING)
    private AvailableDateTimeStatus availableDateTimeStatus;

    public AvailableDateTime(Coach coach, LocalDateTime localDateTime,
                             AvailableDateTimeStatus availableDateTimeStatus) {
        this.coach = coach;
        this.localDateTime = localDateTime;
        this.availableDateTimeStatus = availableDateTimeStatus;
    }

    public void open() {
        this.availableDateTimeStatus = OPEN;
    }

    public void used() {
        this.availableDateTimeStatus = USED;
    }
}
