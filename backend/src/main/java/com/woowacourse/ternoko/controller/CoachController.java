package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.dto.ScheduleResponse;
import com.woowacourse.ternoko.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/coaches")
public class CoachController {

    private final ReservationService reservationService;

    @GetMapping("/{coachId}/schedules")
    public ResponseEntity<ScheduleResponse> findAllReservationByCoach(@PathVariable Long coachId) {
        final ScheduleResponse schedules = reservationService.findAllByCoach(coachId);
        return ResponseEntity.ok(schedules);
    }
}
