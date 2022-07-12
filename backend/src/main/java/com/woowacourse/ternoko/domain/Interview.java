package com.woowacourse.ternoko.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "interview_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate interviewDate;

    @Column(nullable = false)
    private LocalTime interviewStartTime;

    @Column(nullable = false)
    private LocalTime interviewEndTime;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member coach;

    @Column(nullable = false)
    private String crewNickname;

    @OneToMany
    @JoinColumn(name = "formItem_id")
    private List<FormItem> formItems = new ArrayList<>();

    public Interview(LocalDate interviewDate,
                     LocalTime interviewStartTime,
                     LocalTime interviewEndTime,
                     Member coach,
                     String crewNickname,
                     List<FormItem> formItems) {
        this.interviewDate = interviewDate;
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crewNickname = crewNickname;
        this.formItems = formItems;
    }
}
