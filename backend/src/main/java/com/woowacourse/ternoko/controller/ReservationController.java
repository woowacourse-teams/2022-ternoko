package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.config.AuthenticationPrincipal;
import com.woowacourse.ternoko.domain.Reservation;
import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.dto.request.ReservationRequest;
import com.woowacourse.ternoko.service.ReservationService;
import com.woowacourse.ternoko.support.SlackAlarm;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
                                                  @RequestBody final ReservationRequest reservationRequest) throws Exception {
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
}

