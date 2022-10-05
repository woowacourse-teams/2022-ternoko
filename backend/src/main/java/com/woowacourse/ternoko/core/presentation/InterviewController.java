package com.woowacourse.ternoko.core.presentation;

import com.woowacourse.ternoko.auth.presentation.annotation.AuthenticationPrincipal;
import com.woowacourse.ternoko.auth.presentation.annotation.CoachOnly;
import com.woowacourse.ternoko.auth.presentation.annotation.CrewOnly;
import com.woowacourse.ternoko.auth.presentation.annotation.SlackAlarm;
import com.woowacourse.ternoko.core.application.InterviewService;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.dto.request.InterviewRequest;
import com.woowacourse.ternoko.core.dto.response.AvailableDateTimesResponse;
import com.woowacourse.ternoko.core.dto.response.InterviewResponse;
import com.woowacourse.ternoko.core.dto.response.ScheduleResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class InterviewController {

    private final InterviewService interviewService;

    @CrewOnly
    @GetMapping("/interviews")
    public ResponseEntity<List<InterviewResponse>> findAllInterviewsByCrewId(
            @AuthenticationPrincipal final Long crewId) {
        final List<InterviewResponse> interviewResponses = interviewService.findAllByCrewId(crewId);
        return ResponseEntity.ok(interviewResponses);
    }

    @CrewOnly
    @SlackAlarm
    @PostMapping("/interviews")
    public ResponseEntity<Void> createInterview(@AuthenticationPrincipal final Long crewId,
                                                @RequestBody final InterviewRequest interviewRequest) throws Exception {
        final Long interviewId = interviewService.create(crewId, interviewRequest);
        return ResponseEntity.created(URI.create("/api/interviews/" + interviewId)).build();
    }

    @GetMapping("/interviews/{interviewId}")
    public ResponseEntity<InterviewResponse> findInterviewById(@PathVariable final Long interviewId) {
        final InterviewResponse interviewResponse = interviewService.findInterviewResponseById(interviewId);
        return ResponseEntity.ok(interviewResponse);
    }

    @GetMapping("/schedules")
    public ResponseEntity<ScheduleResponse> findAllInterviewByCoach(@AuthenticationPrincipal final Long coachId,
                                                                    @RequestParam final Integer year,
                                                                    @RequestParam final Integer month) {
        final ScheduleResponse schedules = interviewService.findAllByCoach(coachId, year, month);
        return ResponseEntity.ok(schedules);
    }

    @CrewOnly
    @SlackAlarm
    @PutMapping("/interviews/{interviewId}")
    public ResponseEntity<Void> updateInterview(@AuthenticationPrincipal final Long crewId,
                                                @PathVariable final Long interviewId,
                                                @RequestBody final InterviewRequest interviewRequest) throws Exception {
        interviewService.update(crewId, interviewId, interviewRequest);
        return ResponseEntity.ok().build();
    }

    @CrewOnly
    @SlackAlarm
    @DeleteMapping("/interviews/{interviewId}")
    public ResponseEntity<Void> deleteInterview(@AuthenticationPrincipal final Long crewId,
                                                @PathVariable final Long interviewId) throws Exception {
        interviewService.delete(crewId, interviewId);
        return ResponseEntity.noContent().build();
    }

    @CoachOnly
    @SlackAlarm
    @PatchMapping("/interviews/{interviewId}")
    public ResponseEntity<Void> cancelInterview(@AuthenticationPrincipal final Long coachId,
                                                @PathVariable final Long interviewId,
                                                @RequestParam final boolean onlyInterview) throws Exception {
        interviewService.cancelAndDeleteAvailableTime(interviewId, onlyInterview);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/interviews/{interviewId}/calendar/times")
    public ResponseEntity<AvailableDateTimesResponse> findCalendarTimesByInterviewId(
            @PathVariable final Long interviewId,
            @RequestParam final Long coachId,
            @RequestParam final int year,
            @RequestParam final int month) {
        final List<AvailableDateTime> availableDateTimes = interviewService
                .findAvailableDateTimesByCoachIdOrInterviewId(interviewId, coachId, year, month);
        final AvailableDateTimesResponse response = AvailableDateTimesResponse.from(availableDateTimes);
        return ResponseEntity.ok(response);
    }
}
