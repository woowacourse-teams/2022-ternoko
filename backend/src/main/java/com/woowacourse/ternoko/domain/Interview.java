package com.woowacourse.ternoko.domain;

import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.domain.member.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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

//    @OneToOne
//    @JoinColumn(name = "member_id")
//    private Member coach;

    @OneToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;

//    @Column(nullable = false)
//    private String crewNickname;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL)
    private List<FormItem> formItems = new ArrayList<>();


    public Interview(final LocalDateTime interviewStartTime,
                     final LocalDateTime interviewEndTime,
                     final Coach coach,
                     final Crew crew,
                     final List<FormItem> formItems) {
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crew = crew;
        this.formItems = formItems;
    }

    public Interview(final Long id,
                     final LocalDateTime interviewStartTime,
                     final LocalDateTime interviewEndTime,
                     final Coach coach,
                     final Crew crew) {
        this.id = id;
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crew = crew;
    }

    public Interview(final LocalDateTime interviewStartTime,
                     final LocalDateTime interviewEndTime,
                     final Coach coach,
                     final Crew crew) {
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crew = crew;
    }

    public Interview update(Interview updateInterview) {
        return new Interview(this.id,
                updateInterview.getInterviewStartTime(),
                updateInterview.getInterviewEndTime(),
                updateInterview.getCoach(),
                updateInterview.getCrew());
    }
//    public Interview(LocalDateTime interviewStartTime,
//            LocalDateTime interviewEndTime,
//            Member coach,
//            String crewNickname,
//            List<FormItem> formItems) {
//        this.interviewStartTime = interviewStartTime;
//        this.interviewEndTime = interviewEndTime;
//        this.coach = coach;
//        this.crewNickname = crewNickname;
//        this.formItems = formItems;
//    }

//    public Interview(LocalDateTime interviewStartTime,
//            LocalDateTime interviewEndTime,
//            Member coach,
//            String crewNickname) {
//        this.interviewStartTime = interviewStartTime;
//        this.interviewEndTime = interviewEndTime;
//        this.coach = coach;
//        this.crewNickname = crewNickname;
//    }
}
