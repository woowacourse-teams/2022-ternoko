package com.woowacourse.ternoko.interview.domain;

import static com.woowacourse.ternoko.common.exception.ExceptionType.CANNOT_EDIT_INTERVIEW;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_MEMBER_ID;
import static com.woowacourse.ternoko.domain.InterviewStatusType.FIXED;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.InterviewStatusException;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.domain.InterviewStatusType;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.domain.member.Member;
import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
import com.woowacourse.ternoko.login.domain.MemberType;
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

    public void update(Interview updateInterview) {
        validateInterviewStatus(FIXED, CANNOT_EDIT_INTERVIEW);
        this.interviewStartTime = updateInterview.getInterviewStartTime();
        this.interviewEndTime = updateInterview.getInterviewEndTime();
        this.coach = updateInterview.getCoach();
        this.crew = updateInterview.getCrew();
        this.interviewStatusType = updateInterview.getInterviewStatusType();
    }

    public void cancel() {
        this.interviewStatusType = InterviewStatusType.CANCELED;
    }

    public void complete(final MemberType memberType) {
        if (this.interviewStatusType == InterviewStatusType.COMMENT && memberType == MemberType.COACH) {
            this.interviewStatusType = InterviewStatusType.COACH_COMPLETED;
            return;
        }
        if (this.interviewStatusType == InterviewStatusType.COMMENT && memberType == MemberType.CREW) {
            this.interviewStatusType = InterviewStatusType.CREW_COMPLETED;
            return;
        }
        this.interviewStatusType = InterviewStatusType.COMPLETE;
    }

    public boolean sameCrew(final Long crewId) {
        return crew.sameMember(crewId);
    }

    public boolean sameCoach(final Long coachId) {
        return coach.sameMember(coachId);
    }

    public void updateStatus(final InterviewStatusType interviewStatusType) {
        this.interviewStatusType = interviewStatusType;
    }

    private void validateInterviewStatus(final InterviewStatusType interviewStatusType,
                                         final ExceptionType exceptionType) {
        if (this.interviewStatusType == interviewStatusType) {
            throw new InterviewStatusException(exceptionType);
        }
    }

    public Member validateMember(final Long memberId) {
        if (this.coach.sameMember(memberId)) {
            return this.coach;
        }
        if (this.crew.sameMember(memberId)) {
            return this.crew;
        }
        throw new MemberNotFoundException(INVALID_INTERVIEW_MEMBER_ID);
    }
}
