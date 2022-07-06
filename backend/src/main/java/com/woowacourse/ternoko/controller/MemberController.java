package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.domain.Member;
import com.woowacourse.ternoko.dto.CoachResponse;
import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
