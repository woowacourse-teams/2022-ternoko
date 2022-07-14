package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.domain.AvailableDateTime;
import com.woowacourse.ternoko.domain.Member;
import com.woowacourse.ternoko.dto.CalendarRequest;
import com.woowacourse.ternoko.dto.CalendarResponse;
import com.woowacourse.ternoko.dto.CoachResponse;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/reservations/coaches")
    public ResponseEntity<CoachesResponse> findCoaches() {
        final List<Member> coaches = memberService.findCoaches();
        final List<CoachResponse> coachResponses = coaches.stream()
                .map(member -> CoachResponse.coachResponseBuilder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .imageUrl(member.getImageUrl())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CoachesResponse(coachResponses));
    }

    @PutMapping("/coaches/{coachId}/calendar/times")
    public ResponseEntity<Void> saveCalendarTimes(@PathVariable final Long coachId,
                                                  @RequestBody final CalendarRequest calendarRequest) {
        memberService.putAvailableDateTimesByCoachId(coachId, calendarRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/coaches/{coachId}/calendar/times")
    public ResponseEntity<CalendarResponse> findCalendarTimes(@PathVariable final Long coachId) {
        final List<AvailableDateTime> availableDateTimes = memberService.findAvailableDateTimesByCoachId(coachId);
        final CalendarResponse from = CalendarResponse.from(availableDateTimes);
        return ResponseEntity.ok(from);
    }
}
