package com.woowacourse.ternoko.interview.domain;

import static com.woowacourse.ternoko.common.exception.ExceptionType.CANNOT_EDIT_INTERVIEW;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_MEMBER_ID;
import static com.woowacourse.ternoko.domain.member.MemberType.COACH;
import static com.woowacourse.ternoko.domain.member.MemberType.CREW;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.CANCELED;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.COACH_COMPLETED;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.COMPLETE;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.CREW_COMPLETED;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.EDITABLE;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.FIXED;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.InterviewStatusException;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.domain.member.MemberType;
import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EqualsAndHashCode
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

    @OneToMany(mappedBy = "interview", cascade = CascadeType.PERSIST)
    private List<FormItem> formItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private InterviewStatusType interviewStatusType;


    public static Interview from(final LocalDateTime interviewDatetime, final Crew crew, final Coach coach,
                                 final List<FormItem> formItems) {
        return new Interview(
                interviewDatetime,
                interviewDatetime.plusMinutes(30),
                coach,
                crew,
                formItems);
    }

    public Interview(final LocalDateTime interviewStartTime,
                     final LocalDateTime interviewEndTime,
                     final Coach coach, final Crew crew,
                     final List<FormItem> formItems) {
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crew = crew;
        this.formItems = new ArrayList<>(formItems);
        this.interviewStatusType = EDITABLE;
    }

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
        this.formItems = new ArrayList<>(formItems);
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
        this.interviewStatusType = EDITABLE;
    }

    public void update(Interview interview) {
        validateInterviewStatus(FIXED, CANNOT_EDIT_INTERVIEW);
        this.interviewStartTime = interview.getInterviewStartTime();
        this.interviewEndTime = interview.getInterviewEndTime();
        this.coach = interview.getCoach();
        updateFormItem(interview.getFormItems());
    }

    public void updateFormItem(final List<FormItem> formItem) {
        //TODO : 일급컬렉션으로 빼기~
        for (int i = 0; i < formItem.size(); i++) {
            this.formItems.get(i).update(formItem.get(i));
        }
    }

    public void cancel() {
        this.interviewStatusType = CANCELED;
    }

    public void complete(final MemberType memberType) {
        if (this.interviewStatusType == FIXED && memberType == COACH) {
            this.interviewStatusType = COACH_COMPLETED;
            return;
        }
        if (this.interviewStatusType == FIXED && memberType == CREW) {
            this.interviewStatusType = CREW_COMPLETED;
            return;
        }
        this.interviewStatusType = COMPLETE;
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

    public MemberType findMemberType(final Long memberId) {
        if (coach.sameMember(memberId)) {
            return coach.getMemberType();
        }

        if (crew.sameMember(memberId)) {
            return crew.getMemberType();
        }

        throw new MemberNotFoundException(INVALID_INTERVIEW_MEMBER_ID);
    }

    public boolean canCreateCommentBy(final MemberType memberType) {
        return interviewStatusType.canCreateCommentStatusBy(memberType);
    }

    public boolean canFindCommentBy() {
        return interviewStatusType.canFindCommentBy();
    }

    public boolean isSame(final Long interviewId) {
        return id.equals(interviewId);
    }
}
