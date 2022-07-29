package com.woowacourse.ternoko.domain;

import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
    private LocalDateTime interviewStartTime;

    @Column(nullable = false)
    private LocalDateTime interviewEndTime;

    @OneToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @OneToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.DETACH)
    private List<FormItem> formItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private InterviewStatusType interviewStatusType;

    public Interview(final LocalDateTime interviewStartTime,
                     final LocalDateTime interviewEndTime,
                     final Coach coach,
                     final Crew crew,
                     final List<FormItem> formItems,
                     final InterviewStatusType interviewStatusType) {
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crew = crew;
        this.formItems = formItems;
        this.interviewStatusType = interviewStatusType;
    }

    public Interview(final Long id,
                     final LocalDateTime interviewStartTime,
                     final LocalDateTime interviewEndTime,
                     final Coach coach,
                     final Crew crew,
                     final InterviewStatusType interviewStatusType) {
        this.id = id;
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crew = crew;
        this.interviewStatusType = interviewStatusType;
    }

    public Interview(final LocalDateTime interviewStartTime,
                     final LocalDateTime interviewEndTime,
                     final Coach coach,
                     final Crew crew) {
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crew = crew;
        this.interviewStatusType = InterviewStatusType.EDITABLE;
    }

    public Interview update(Interview updateInterview) {
        return new Interview(this.id,
                updateInterview.getInterviewStartTime(),
                updateInterview.getInterviewEndTime(),
                updateInterview.getCoach(),
                updateInterview.getCrew(),
                updateInterview.interviewStatusType);
    }

    public Interview cancel() {
        return new Interview(this.id,
                this.getInterviewStartTime(),
                this.getInterviewEndTime(),
                this.getCoach(),
                this.getCrew(),
                InterviewStatusType.CANCELED);
    }

    public boolean sameCrew(Long crewId) {
        return crew.sameMember(crewId);
    }

    public boolean sameCoach(Long coachId) {
        return coach.sameMember(coachId);
    }
}
