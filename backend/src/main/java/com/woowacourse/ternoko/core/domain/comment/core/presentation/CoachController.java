package com.woowacourse.ternoko.core.domain.comment.core.presentation;

import com.woowacourse.ternoko.auth.presentation.annotation.AuthenticationPrincipal;
import com.woowacourse.ternoko.auth.presentation.annotation.CoachOnly;
import com.woowacourse.ternoko.core.domain.comment.core.application.CoachService;
import com.woowacourse.ternoko.core.domain.comment.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.comment.core.dto.request.CalendarRequest;
import com.woowacourse.ternoko.core.domain.comment.core.dto.request.CoachUpdateRequest;
import com.woowacourse.ternoko.core.domain.comment.core.dto.response.AvailableDateTimesResponse;
import com.woowacourse.ternoko.core.domain.comment.core.dto.response.CoachResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/calendar/times")
    public ResponseEntity<com.woowacourse.ternoko.core.domain.comment.core.dto.response.AvailableDateTimesResponse> findCalendarTimes(@RequestParam final Long coachId,
                                                                                                                                      @RequestParam final int year,
                                                                                                                                      @RequestParam final int month) {
        final List<com.woowacourse.ternoko.core.domain.comment.core.domain.availabledatetime.AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachId(coachId, year, month);
        final com.woowacourse.ternoko.core.domain.comment.core.dto.response.AvailableDateTimesResponse response = com.woowacourse.ternoko.core.domain.comment.core.dto.response.AvailableDateTimesResponse
                .from(availableDateTimes);
        return ResponseEntity.ok(response);
    }

    @CoachOnly
    @PutMapping("/calendar/times")
    public ResponseEntity<Void> saveCalendarTimes(@AuthenticationPrincipal final Long coachId,
                                                  @RequestBody final CalendarRequest calendarRequest) {
        coachService.putAvailableDateTimesByCoachId(coachId, calendarRequest);
        return ResponseEntity.ok().build();
    }

    @CoachOnly
    @GetMapping("/coaches/me")
    public ResponseEntity<CoachResponse> findCoach(@AuthenticationPrincipal final Long coachId) {
        return ResponseEntity.ok(coachService.findCoach(coachId));
    }

    @CoachOnly
    @PatchMapping("/coaches/me")
    public ResponseEntity<Void> updateCoach(@AuthenticationPrincipal final Long coachId,
                                            @RequestBody final CoachUpdateRequest coachUpdateRequest) {
        coachService.partUpdateCrew(coachId, coachUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/interviews/{interviewId}/calendar/times")
    public ResponseEntity<com.woowacourse.ternoko.core.domain.comment.core.dto.response.AvailableDateTimesResponse> findCalendarTimesByInterviewId(
            @PathVariable final Long interviewId,
            @RequestParam final Long coachId,
            @RequestParam final int year,
            @RequestParam final int month) {
        final List<AvailableDateTime> availableDateTimes = coachService
                .findAvailableDateTimesByCoachIdAndInterviewId(interviewId, coachId, year, month);
        final com.woowacourse.ternoko.core.domain.comment.core.dto.response.AvailableDateTimesResponse response = AvailableDateTimesResponse
                .from(availableDateTimes);
        return ResponseEntity.ok(response);
    }
}
