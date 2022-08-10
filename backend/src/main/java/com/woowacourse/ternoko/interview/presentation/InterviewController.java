package com.woowacourse.ternoko.interview.presentation;

import com.woowacourse.ternoko.config.AuthenticationPrincipal;
import com.woowacourse.ternoko.interview.dto.InterviewRequest;
import com.woowacourse.ternoko.interview.application.InterviewService;
import com.woowacourse.ternoko.interview.domain.Interview;
import com.woowacourse.ternoko.interview.dto.InterviewResponse;
import com.woowacourse.ternoko.interview.dto.ScheduleResponse;
import com.woowacourse.ternoko.support.AlarmMessage;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class InterviewController {

    private final InterviewService interviewService;
    private final SlackAlarm slackAlarm;

    @PostMapping("/reservations")
    public ResponseEntity<Void> createInterview(@AuthenticationPrincipal final Long crewId,
                                                @RequestBody final InterviewRequest interviewRequest)
            throws Exception {
        final Interview interview = interviewService.create(crewId, interviewRequest);
        slackAlarm.sendMessage(interview, AlarmMessage.CREW_CREATE.getMessage());
        return ResponseEntity.created(URI.create("/api/reservations/" + interview.getId())).build();
    }

    @GetMapping("/reservations/{interviewId}")
    public ResponseEntity<InterviewResponse> findInterviewById(@PathVariable final Long interviewId) {
        final InterviewResponse interviewResponse = interviewService.findInterviewResponseById(interviewId);

        return ResponseEntity.ok(interviewResponse);
    }

    @GetMapping("/schedules")
    public ResponseEntity<ScheduleResponse> findAllReservationByCoach(@AuthenticationPrincipal final Long coachId,
                                                                      @RequestParam final Integer year,
                                                                      @RequestParam final Integer month) {
        final ScheduleResponse schedules = interviewService.findAllByCoach(coachId, year, month);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<InterviewResponse>> findAllInterviewsByCrewId(
            @AuthenticationPrincipal final Long crewId) {
        final List<InterviewResponse> interviewResponses = interviewService.findAllByCrewId(crewId);
        return ResponseEntity.ok(interviewResponses);
    }

    @PutMapping("/reservations/{interviewId}")
    public ResponseEntity<Void> updateInterview(@AuthenticationPrincipal final Long crewId,
                                                @PathVariable final Long interviewId,
                                                @RequestBody final InterviewRequest interviewRequest)
            throws Exception {
        final Interview updateInterview = interviewService.update(crewId, interviewId, interviewRequest);

        slackAlarm.sendMessage(updateInterview, AlarmMessage.CREW_UPDATE.getMessage());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservations/{interviewId}")
    public ResponseEntity<Void> deleteReservation(@AuthenticationPrincipal final Long crewId,
                                                  @PathVariable final Long interviewId) throws Exception {
        final Interview interview = interviewService.delete(crewId, interviewId);

        slackAlarm.sendMessage(interview, AlarmMessage.CREW_DELETE.getMessage());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reservations/{interviewId}")
    public ResponseEntity<Void> cancelReservation(@AuthenticationPrincipal final Long coachId,
                                                  @PathVariable final Long interviewId) throws Exception {
        final Interview interview = interviewService.cancel(coachId, interviewId);

        slackAlarm.sendMessage(interview, AlarmMessage.COACH_CANCEL.getMessage());

        return ResponseEntity.noContent().build();
    }
}
