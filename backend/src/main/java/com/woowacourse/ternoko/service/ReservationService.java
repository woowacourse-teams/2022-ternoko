package com.woowacourse.ternoko.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.FormItem;
import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.Reservation;
import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.dto.ScheduleResponse;
import com.woowacourse.ternoko.dto.request.FormItemRequest;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.FormItemRepository;
import com.woowacourse.ternoko.repository.InterviewRepository;
import com.woowacourse.ternoko.repository.ReservationRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {

    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int START_HOUR = 0;
    private static final int START_MINUTE = 0;
    private static final int END_HOUR = 23;
    private static final int END_MINUTE = 59;
    public static final String INVALID_LOCAL_DATE_TIME_EXCEPTION_MESSAGE = "면담 예약은 최소 하루 전에 가능 합니다.";

    private final CoachRepository coachRepository;
    private final FormItemRepository formItemRepository;
    private final ReservationRepository reservationRepository;
    private final InterviewRepository interviewRepository;

    public Long create(final Long coachId, final ReservationRequest reservationRequest) {
        final List<FormItemRequest> interviewQuestions = reservationRequest.getInterviewQuestions();

        final Interview interview = convertInterview(coachId, reservationRequest,
                interviewQuestions);

        interviewRepository.save(interview);

        return reservationRepository.save(new Reservation(interview, false)).getId();
    }

    private Interview convertInterview(final Long coachId,
                                       final ReservationRequest reservationRequest,
                                       final List<FormItemRequest> interviewQuestions) {
        final List<FormItem> formItems = convertFormItem(interviewQuestions);

        final LocalDateTime reservationDatetime = reservationRequest.getInterviewDatetime();

        Coach coach = coachRepository.findById(coachId)
            .orElseThrow(() -> new NoSuchElementException("해당하는 코치를 찾을 수 없습니다."));

        validateInterviewStartTime(reservationDatetime);
        return new Interview(
                reservationDatetime,
                reservationDatetime.plusMinutes(30),
                coach,
                reservationRequest.getCrewNickname(),
                formItems);
    }

    private void validateInterviewStartTime(final LocalDateTime localDateTime) {
        final LocalDate standardDay = LocalDate.now().plusDays(1);
        if (!standardDay.isBefore(localDateTime.toLocalDate())) {
            throw new IllegalArgumentException(INVALID_LOCAL_DATE_TIME_EXCEPTION_MESSAGE);
        }
    }

    private List<FormItem> convertFormItem(final List<FormItemRequest> interviewQuestions) {
        final List<FormItem> formItems = interviewQuestions.stream()
                .map(FormItemRequest::toFormItem)
                .collect(Collectors.toList());

        formItemRepository.saveAll(formItems);
        return formItems;
    }

    @Transactional(readOnly = true)
    public ReservationResponse findReservationById(final Long id) {
        final Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 면담 예약입니다."));
        return ReservationResponse.from(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAllReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ScheduleResponse findAllByCoach(final Long coachId, final Integer year, final Integer month) {
        final Integer lastDayOfMonth = LocalDate.of(year, month, FIRST_DAY_OF_MONTH).lengthOfMonth();
        final LocalDateTime startOfMonth = LocalDateTime.of(year, month, FIRST_DAY_OF_MONTH, START_HOUR, START_MINUTE);
        final LocalDateTime endOfMonth = LocalDateTime.of(year, month, lastDayOfMonth, END_HOUR, END_MINUTE);

        final List<Interview> interviews = interviewRepository
                .findAllByCoachIdAndDateRange(startOfMonth, endOfMonth, coachId);

        return ScheduleResponse.from(interviews);
    }
}
