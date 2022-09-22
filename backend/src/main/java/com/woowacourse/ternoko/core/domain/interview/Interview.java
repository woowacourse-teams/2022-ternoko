package com.woowacourse.ternoko.core.domain.interview;

import static com.woowacourse.ternoko.common.exception.ExceptionType.CANNOT_EDIT_INTERVIEW;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_COACH_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_CREW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_INTERVIEW_MEMBER_ID;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.CANCELED;
import static com.woowacourse.ternoko.core.domain.interview.InterviewStatusType.EDITABLE;
import static com.woowacourse.ternoko.core.domain.member.MemberType.COACH;
import static com.woowacourse.ternoko.core.domain.member.MemberType.CREW;

import com.woowacourse.ternoko.common.exception.InterviewStatusException;
import com.woowacourse.ternoko.common.exception.InvalidInterviewCoachIdException;
import com.woowacourse.ternoko.common.exception.InvalidInterviewCrewIdException;
import com.woowacourse.ternoko.common.exception.MemberNotFoundException;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItems;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "interview_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "available_date_time_id")
    private AvailableDateTime availableDateTime;

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
        validateUpdateInterviewStatus();
        this.interviewStartTime = interview.getInterviewStartTime();
        this.interviewEndTime = interview.getInterviewEndTime();
        this.coach = interview.getCoach();
        this.interviewStatusType = interview.getInterviewStatusType();
        formItems.update(interview.getFormItems());
    }

    private void validateCrew(final Long crewId) {
        if (!crew.isSameId(crewId)) {
            throw new InvalidInterviewCrewIdException(INVALID_INTERVIEW_CREW_ID);
        }
    }

    private void validateUpdateInterviewStatus() {
        final List<InterviewStatusType> invalidUpdateStatus = findInvalidUpdateStatus();
        for (InterviewStatusType invalidStatus : invalidUpdateStatus) {
            validateInterviewStatus(invalidStatus);
        }
    }

    private List<InterviewStatusType> findInvalidUpdateStatus() {
        final List<InterviewStatusType> status = Arrays.stream(InterviewStatusType.values())
                .collect(Collectors.toList());
        status.remove(EDITABLE);
        status.remove(CANCELED);
        return status;
    }

    private void validateInterviewStatus(final InterviewStatusType invalidStatus) {
        if (this.interviewStatusType == invalidStatus) {
            throw new InterviewStatusException(CANNOT_EDIT_INTERVIEW);
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
        if (this.interviewStatusType == InterviewStatusType.FIXED && memberType == COACH) {
            this.interviewStatusType = InterviewStatusType.COACH_COMPLETED;
            return;
        }
        if (this.interviewStatusType == InterviewStatusType.FIXED && memberType == CREW) {
            this.interviewStatusType = InterviewStatusType.CREW_COMPLETED;
            return;
        }
        this.interviewStatusType = InterviewStatusType.COMPLETED;
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
                this.availableDateTime,
                this.interviewStartTime,
                this.interviewEndTime,
                this.coach,
                this.crew,
                this.formItems,
                this.interviewStatusType);
    }
}

