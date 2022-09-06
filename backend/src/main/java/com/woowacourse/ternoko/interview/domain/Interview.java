package com.woowacourse.ternoko.interview.domain;

import static com.woowacourse.ternoko.common.exception.type.ExceptionType.CANNOT_EDIT_INTERVIEW;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_COACH_ID;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_CREW_ID;
import static com.woowacourse.ternoko.common.exception.type.ExceptionType.INVALID_INTERVIEW_MEMBER_ID;
import static com.woowacourse.ternoko.domain.member.MemberType.COACH;
import static com.woowacourse.ternoko.domain.member.MemberType.CREW;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.CANCELED;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.COACH_COMPLETED;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.COMPLETE;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.CREW_COMPLETED;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.EDITABLE;
import static com.woowacourse.ternoko.interview.domain.InterviewStatusType.FIXED;

import com.woowacourse.ternoko.common.exception.InterviewStatusException;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.common.exception.type.ExceptionType;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.domain.member.MemberType;
import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
import com.woowacourse.ternoko.interview.domain.formitem.FormItems;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewCoachIdException;
import com.woowacourse.ternoko.interview.exception.InvalidInterviewCrewIdException;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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

    @Embedded
    private FormItems formItems;

    @Enumerated(EnumType.STRING)
    private InterviewStatusType interviewStatusType;

    public Interview(final Long id,
                     final LocalDateTime interviewStartTime,
                     final LocalDateTime interviewEndTime,
                     final Coach coach,
                     final Crew crew,
                     final List<FormItem> formItems,
                     final InterviewStatusType interviewStatusType) {
        this.id = id;
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.coach = coach;
        this.crew = crew;
        this.formItems = new FormItems(formItems, this);
        this.interviewStatusType = interviewStatusType;
    }

    public Interview(final LocalDateTime interviewStartTime,
                     final LocalDateTime interviewEndTime,
                     final Coach coach,
                     final Crew crew,
                     final List<FormItem> formItems) {
        this(null, interviewStartTime, interviewEndTime, coach, crew, formItems, EDITABLE);
    }

    public static Interview of(final LocalDateTime interviewDatetime,
                               final Coach coach,
                               final Crew crew,
                               final List<FormItem> formItems) {
        return new Interview(
                interviewDatetime,
                interviewDatetime.plusMinutes(30),
                coach,
                crew,
                formItems);
    }

    public void update(Interview interview) {
        validateCrew(interview.crew.getId());
        validateInterviewStatus(FIXED, CANNOT_EDIT_INTERVIEW);
        this.interviewStartTime = interview.getInterviewStartTime();
        this.interviewEndTime = interview.getInterviewEndTime();
        this.coach = interview.getCoach();
        formItems.update(interview.getFormItems());
    }

    private void validateCrew(final Long crewId) {
        if (!crew.isSameId(crewId)) {
            throw new InvalidInterviewCrewIdException(INVALID_INTERVIEW_CREW_ID);
        }
    }

    private void validateInterviewStatus(final InterviewStatusType interviewStatusType,
                                         final ExceptionType exceptionType) {
        if (this.interviewStatusType == interviewStatusType) {
            throw new InterviewStatusException(exceptionType);
        }
    }

    public void cancel(final Long coachId) {
        validateCoach(coachId);
        this.interviewStatusType = CANCELED;
    }

    private void validateCoach(final Long coachId) {
        if (!coach.isSameId(coachId)) {
            throw new InvalidInterviewCoachIdException(INVALID_INTERVIEW_COACH_ID);
        }
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

    public void updateStatus(final InterviewStatusType interviewStatusType) {
        this.interviewStatusType = interviewStatusType;
    }

    public MemberType findMemberType(final Long memberId) {
        if (coach.isSameId(memberId)) {
            return coach.getMemberType();
        }

        if (crew.isSameId(memberId)) {
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

    public boolean isCreatedBy(final Long crewId) {
        return crew.isSameId(crewId);
    }

    public List<FormItem> getFormItems() {
        return formItems.getFormItems();
    }

    public Interview copyOf() {
        return new Interview(this.id,
                this.interviewStartTime,
                this.interviewEndTime,
                this.coach,
                this.crew,
                this.formItems,
                this.interviewStatusType);
    }
}

