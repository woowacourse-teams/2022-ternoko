package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.config.AuthenticationPrincipal;
import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.Reservation;
import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import com.woowacourse.ternoko.service.ReservationService;
import com.woowacourse.ternoko.support.SlackAlarm;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final SlackAlarm slackAlarm;

    @PostMapping
    public ResponseEntity<Void> createReservation(@AuthenticationPrincipal final Long crewId,
                                                  @RequestBody final ReservationRequest reservationRequest)
            throws Exception {
        final Reservation reservation = reservationService.create(crewId, reservationRequest);
        slackAlarm.sendAlarmWhenCreatedReservationToCrew(reservation);
        slackAlarm.sendAlarmWhenCreatedReservationToCoach(reservation);
        return ResponseEntity.created(URI.create("/api/reservations/" + reservation.getId())).build();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> findReservationById(@PathVariable final Long reservationId) {
        final ReservationResponse reservationResponse = reservationService.findReservationById(reservationId);

        return ResponseEntity.ok(reservationResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAllReservations() {
        final List<ReservationResponse> reservationResponses = reservationService.findAllReservations();

        return ResponseEntity.ok(reservationResponses);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Void> updateReservation(@AuthenticationPrincipal final Long crewId,
                                                  @PathVariable Long reservationId,
                                                  @RequestBody final ReservationRequest reservationRequest)
            throws Exception {
        Reservation updateReservation = reservationService.update(crewId, reservationId, reservationRequest);
        slackAlarm.sendAlarmWhenUpdatedReservationToCrew(updateReservation);
        slackAlarm.sendAlarmWhenUpdatedReservationToCoach(updateReservation);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@AuthenticationPrincipal final Long crewId,
                                                  @PathVariable Long reservationId) throws Exception {
        Reservation reservation = reservationService.delete(crewId, reservationId);
        slackAlarm.sendAlarmWhenDeletedReservationToCrew(reservation);
        slackAlarm.sendAlarmWhenDeletedReservationToCoach(reservation);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@AuthenticationPrincipal final Long coachId,
                                                  @PathVariable Long reservationId) throws Exception {
        Interview interview = reservationService.cancel(coachId, reservationId);
        slackAlarm.sendAlarmWhenCanceledReservationToCrew(interview);
        slackAlarm.sendAlarmWhenCanceledReservationToCoach(interview);
        return ResponseEntity.noContent().build();
    }
}
