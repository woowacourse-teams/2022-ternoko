package com.woowacourse.ternoko.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @Column(nullable = false)
    private boolean isAccepted;

    public Reservation(Interview interview, boolean isAccepted) {
        this.interview = interview;
        this.isAccepted = isAccepted;
    }
}
