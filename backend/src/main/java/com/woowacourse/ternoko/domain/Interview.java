package com.woowacourse.ternoko.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Interview {

    protected static final String INVALID_LOCAL_DATE_TIME_EXCEPTION_MESSAGE = "면담 예약은 최소 하루 전에 가능 합니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "interview_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime interviewStartTime;

    @Column(nullable = false)
    private LocalDateTime interviewEndTime;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member coach;

    @Column(nullable = false)
    private String crewNickname;

    @OneToMany
    @JoinColumn(name = "formItem_id")
    private List<FormItem> formItems = new ArrayList<>();

    public Interview(LocalDateTime interviewStartTime,
                     LocalDateTime interviewEndTime,
                     Member coach,
                     String crewNickname,
                     List<FormItem> formItems) {
        validateInterviewStartTime(interviewStartTime);
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crewNickname = crewNickname;
        this.formItems = formItems;
    }

    private void validateInterviewStartTime(LocalDateTime localDateTime) {
        final LocalDate standardDay = LocalDate.now().plusDays(1);
        if (!standardDay.isBefore(localDateTime.toLocalDate())) {
            throw new IllegalArgumentException(INVALID_LOCAL_DATE_TIME_EXCEPTION_MESSAGE);
        }
    }
}
