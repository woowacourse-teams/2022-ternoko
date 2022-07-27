package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.dto.NicknameResponse;
import com.woowacourse.ternoko.service.CoachService;
import com.woowacourse.ternoko.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final CoachService coachService;
    private final MemberService memberService;

    @GetMapping("/reservations/coaches")
    public ResponseEntity<CoachesResponse> findCoaches() {
        return ResponseEntity.ok(coachService.findCoaches());
    }

    @GetMapping("/login/check")
    public ResponseEntity<NicknameResponse> checkUniqueNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(memberService.hasNickname(nickname));
    }
}
