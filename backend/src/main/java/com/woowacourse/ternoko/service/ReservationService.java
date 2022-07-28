package com.woowacourse.ternoko.service;

import static com.woowacourse.ternoko.common.exception.ExceptionType.COACH_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.CREW_NOT_FOUND;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_AVAILABLE_DATE_TIME;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_RESERVATION_CREW_ID;
import static com.woowacourse.ternoko.common.exception.ExceptionType.INVALID_RESERVATION_DATE;
import static com.woowacourse.ternoko.common.exception.ExceptionType.RESERVATION_NOT_FOUND;

import com.woowacourse.ternoko.common.exception.CoachNotFoundException;
import com.woowacourse.ternoko.common.exception.CrewNotFoundException;
import com.woowacourse.ternoko.common.exception.InvalidReservationCrewIdException;
import com.woowacourse.ternoko.common.exception.InvalidReservationDateException;
import com.woowacourse.ternoko.common.exception.ReservationNotFoundException;
import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.domain.FormItem;
import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.Reservation;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.dto.ScheduleResponse;
import com.woowacourse.ternoko.dto.request.FormItemRequest;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import com.woowacourse.ternoko.repository.AvailableDateTimeRepository;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.CrewRepository;
import com.woowacourse.ternoko.repository.FormItemRepository;
import com.woowacourse.ternoko.repository.InterviewRepository;
import com.woowacourse.ternoko.repository.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final CrewRepository crewRepository;
    private final ReservationRepository reservationRepository;
    private final InterviewRepository interviewRepository;
    private final AvailableDateTimeRepository availableDateTimeRepository;
    private final FormItemRepository formItemRepository;

    public Reservation create(final Long crewId, final ReservationRequest reservationRequest) {
        final Interview interview = convertInterview(crewId, reservationRequest);
        final Interview savedInterview = interviewRepository.save(interview);

        final List<FormItem> formItems = convertFormItem(reservationRequest.getInterviewQuestions());
        for (FormItem formItem : formItems) {
            formItem.addInterview(savedInterview);
        }
        formItemRepository.saveAll(formItems);
        ;
        final AvailableDateTime availableDateTime = findAvailableTime(reservationRequest);
        availableDateTimeRepository.delete(availableDateTime);

        return reservationRepository.save(new Reservation(interview, false));
    }

    private Interview convertInterview(final Long crewId, final ReservationRequest reservationRequest) {
        final LocalDateTime reservationDatetime = reservationRequest.getInterviewDatetime();

        final Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new CrewNotFoundException(CREW_NOT_FOUND, crewId));

        final Coach coach = coachRepository.findById(reservationRequest.getCoachId())
                .orElseThrow(() -> new CoachNotFoundException(COACH_NOT_FOUND, reservationRequest.getCoachId()));

        validateInterviewStartTime(reservationDatetime);

        return new Interview(
                reservationDatetime,
                reservationDatetime.plusMinutes(30),
                coach,
                crew);
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
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND, reservationId));
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

    public Reservation update(final Long crewId,
                              final Long reservationId,
                              final ReservationRequest reservationRequest) {
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND, reservationId));
        if (!reservation.getInterview().getCrew().getId().equals(crewId)) {
            throw new InvalidReservationCrewIdException(INVALID_RESERVATION_CREW_ID);
        }

        Interview updateInterviewRequest = convertInterview(crewId, reservationRequest);
        List<FormItem> updateInterviewFormItemsRequest = convertFormItem(reservationRequest.getInterviewQuestions());

        final AvailableDateTime availableDateTime = findAvailableTime(reservationRequest);

        Interview originalInterview = reservation.getInterview();
        List<FormItem> originalInterviewFormItems = originalInterview.getFormItems();

        List<FormItem> updateFormItems = new ArrayList<>();
        for (int i = 0; i < originalInterviewFormItems.size(); i++) {
            updateFormItems.add(originalInterviewFormItems.get(i)
                    .update(updateInterviewFormItemsRequest.get(i), originalInterview));
        }
        formItemRepository.saveAll(updateFormItems);

        Interview updatedInterview = interviewRepository.save(originalInterview.update(updateInterviewRequest));

        for (FormItem formItem : updateFormItems) {
            formItem.addInterview(updatedInterview);
        }
        availableDateTimeRepository.delete(availableDateTime);

        return reservationRepository.save(reservation.update(updatedInterview));
    }

    private AvailableDateTime findAvailableTime(ReservationRequest reservationRequest) {
        return availableDateTimeRepository.findByCoachIdAndInterviewDateTime(reservationRequest.getCoachId(),
                        reservationRequest.getInterviewDatetime())
                .orElseThrow(() -> new InvalidReservationDateException(INVALID_AVAILABLE_DATE_TIME));
    }
}
