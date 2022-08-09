package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.config.AuthenticationPrincipal;
import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.dto.AvailableDateTimesResponse;
import com.woowacourse.ternoko.dto.CoachResponse;
import com.woowacourse.ternoko.dto.CoachUpdateRequest;
import com.woowacourse.ternoko.dto.request.AvailableDateTimesRequest;
import com.woowacourse.ternoko.service.CoachService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CoachController {

    private final CoachService coachService;

    @PutMapping("/calendar/times")
    public ResponseEntity<Void> saveCalendarTimes(@AuthenticationPrincipal final Long coachId,
                                                  @RequestBody final AvailableDateTimesRequest availableDateTimesRequest) {
        coachService.putAvailableDateTimesByCoachId(coachId, availableDateTimesRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/calendar/times")
    public ResponseEntity<AvailableDateTimesResponse> findCalendarTimes(@RequestParam final Long coachId,
                                                                        @RequestParam final int year,
                                                                        @RequestParam final int month) {
        final List<AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachId(coachId, year, month);
        final AvailableDateTimesResponse from = AvailableDateTimesResponse.from(availableDateTimes);
        return ResponseEntity.ok(from);
    }

    @GetMapping("/coaches/me")
    public ResponseEntity<CoachResponse> findCoach(@AuthenticationPrincipal final Long coachId) {
        return ResponseEntity.ok(coachService.findCoach(coachId));
    }

    @PatchMapping("/coaches/me")
    public ResponseEntity<Void> updateCoach(@AuthenticationPrincipal final Long coachId,
                                            @RequestBody final CoachUpdateRequest coachUpdateRequest) {
        coachService.partUpdateCrew(coachId, coachUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
