package com.woowacourse.ternoko.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.dto.AvailableDateTimesResponse;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;
import com.woowacourse.ternoko.service.CoachService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final CoachService coachService;

    @GetMapping("/reservations/coaches")
    public ResponseEntity<CoachesResponse> findCoaches() {
        return ResponseEntity.ok(coachService.findCoaches());
    }

    @PutMapping("/coaches/{coachId}/calendar/times")
    public ResponseEntity<Void> saveCalendarTimes(@PathVariable final Long coachId,
        @RequestBody final AvailableDateTimesRequest availableDateTimesRequest) {
        coachService.putAvailableDateTimesByCoachId(coachId, availableDateTimesRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/coaches/{coachId}/calendar/times")
    public ResponseEntity<AvailableDateTimesResponse> findCalendarTimes(@PathVariable final Long coachId) {
        final List<AvailableDateTime> availableDateTimes = coachService.findAvailableDateTimesByCoachId(coachId);
        final AvailableDateTimesResponse from = AvailableDateTimesResponse.from(availableDateTimes);
        return ResponseEntity.ok(from);
    }
}
