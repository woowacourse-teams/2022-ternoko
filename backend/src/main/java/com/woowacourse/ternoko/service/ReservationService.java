package com.woowacourse.ternoko.service;

import com.woowacourse.ternoko.domain.FormItem;
import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.Location;
import com.woowacourse.ternoko.domain.Member;
import com.woowacourse.ternoko.domain.Reservation;
import com.woowacourse.ternoko.dto.FormItemDto;
import com.woowacourse.ternoko.dto.ReservationRequest;
import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.repository.FormItemRepository;
import com.woowacourse.ternoko.repository.InterviewRepository;
import com.woowacourse.ternoko.repository.MemberRepository;
import com.woowacourse.ternoko.repository.ReservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {

    private final MemberRepository memberRepository;
    private final FormItemRepository formItemRepository;
    private final ReservationRepository reservationRepository;
    private final InterviewRepository interviewRepository;

    public Long create(final Long coachId, final ReservationRequest reservationRequest) {
        final List<FormItemDto> interviewQuestions = reservationRequest.getInterviewQuestions();

        final Interview interview = convertInterview(coachId, reservationRequest,
                interviewQuestions);

        interviewRepository.save(interview);

        return reservationRepository.save(new Reservation(interview, false)).getId();
    }

    private Interview convertInterview(final Long coachId,
                                       final ReservationRequest reservationRequest,
                                       final List<FormItemDto> interviewQuestions) {
        final List<FormItem> formItems = convertFormItem(interviewQuestions);

        final LocalDateTime reservationDatetime = reservationRequest.getInterviewDatetime();

        final Member coach = memberRepository.findById(coachId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 코치를 찾을 수 없습니다."));

        return new Interview(reservationDatetime.toLocalDate(),
                reservationDatetime.toLocalTime(),
                reservationDatetime.toLocalTime().plusMinutes(30),
                coach,
                reservationRequest.getCrewNickname(),
                Location.from(reservationRequest.getLocation()),
                formItems);
    }

    private List<FormItem> convertFormItem(final List<FormItemDto> interviewQuestions) {
        final List<FormItem> formItems = interviewQuestions.stream()
                .map(FormItemDto::toFormItem)
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
}
