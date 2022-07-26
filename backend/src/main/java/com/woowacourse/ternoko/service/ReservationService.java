package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.common.exception.ExceptionType.*;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.InvalidReservationDateException;
import com.woowacourse.ternoko.common.exception.ReservationNotFoundException;
import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.domain.FormItem;
import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.Reservation;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.dto.ScheduleResponse;
import com.woowacourse.ternoko.dto.request.FormItemRequest;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import com.woowacourse.ternoko.repository.AvailableDateTimeRepository;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.InterviewRepository;
import com.woowacourse.ternoko.repository.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {

    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int START_HOUR = 0;
    private static final int START_MINUTE = 0;
    private static final int END_HOUR = 23;
    private static final int END_MINUTE = 59;

    private final CoachRepository coachRepository;
    private final ReservationRepository reservationRepository;
    private final InterviewRepository interviewRepository;
    private final AvailableDateTimeRepository availableDateTimeRepository;

    public Long create(final Long coachId, final ReservationRequest reservationRequest) {
        final Interview interview = convertInterview(coachId, reservationRequest);
        final Interview savedInterview = interviewRepository.save(interview);

        final List<FormItem> formItems = convertFormItem(reservationRequest.getInterviewQuestions());
        for (FormItem formItem : formItems) {
            formItem.addInterview(savedInterview);
        }

        AvailableDateTime availableDateTime = availableDateTimeRepository.findByCoachIdAndInterviewDateTime(
                        coachId, reservationRequest.getInterviewDatetime())
                .orElseThrow(() -> new InvalidReservationDateException(INVALID_AVAILABLE_DATE_TIME));
        availableDateTimeRepository.delete(availableDateTime);

        return reservationRepository.save(new Reservation(interview, false)).getId();
    }

    private Interview convertInterview(final Long coachId, final ReservationRequest reservationRequest) {
        final LocalDateTime reservationDatetime = reservationRequest.getInterviewDatetime();

        final Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new CoachNotFoundException(COACH_NOT_FOUND, coachId));

        validateInterviewStartTime(reservationDatetime);

        return new Interview(
                reservationDatetime,
                reservationDatetime.plusMinutes(30),
                coach,
                reservationRequest.getCrewNickname());
    }

    private void validateInterviewStartTime(final LocalDateTime localDateTime) {
        //TODO: 날짜 컨트롤러에서 받아서 검증하는걸로 변경
        final LocalDate standardDay = LocalDate.now().plusDays(1);
        if (!standardDay.isBefore(localDateTime.toLocalDate())) {
            throw new InvalidReservationDateException(INVALID_RESERVATION_DATE);
        }
    }

    private List<FormItem> convertFormItem(final List<FormItemRequest> interviewQuestions) {
        return interviewQuestions.stream()
                .map(FormItemRequest::toFormItem)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReservationResponse findReservationById(final Long reservationId) {
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(
                        () -> new ReservationNotFoundException(RESERVATION_NOT_FOUND, reservationId));
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
