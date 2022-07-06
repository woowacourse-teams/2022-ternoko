package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.dto.ReservationRequest;
import com.woowacourse.ternoko.dto.ReservationResponse;
import com.woowacourse.ternoko.service.ReservationService;
import java.net.URI;
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
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations/coaches/{coachId}")
    public ResponseEntity<Void> createReservation(@PathVariable final Long coachId,
                                                  @RequestBody final ReservationRequest reservationRequest) {
        final Long reservationId = reservationService.create(coachId, reservationRequest);

        return ResponseEntity.created(URI.create("/api/reservations/" + reservationId)).build();
    }

    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationResponse> findReservationById(@PathVariable final Long reservationId) {
        final ReservationResponse reservationResponse = reservationService.findReservationById(reservationId);

        return ResponseEntity.ok(reservationResponse);
    }
}

