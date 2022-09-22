package com.woowacourse.ternoko.core.presentation;

import com.woowacourse.ternoko.core.application.CoachService;
import com.woowacourse.ternoko.core.application.MemberService;
import com.woowacourse.ternoko.core.dto.response.CoachesResponse;
import com.woowacourse.ternoko.core.dto.response.NicknameResponse;
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

    @GetMapping("/coaches")
    public ResponseEntity<CoachesResponse> findCoaches() {
        return ResponseEntity.ok(coachService.findCoaches());
    }

    @GetMapping("/login/check")
    public ResponseEntity<NicknameResponse> checkUniqueNickname(@RequestParam final String nickname) {
        return ResponseEntity.ok(memberService.hasNickname(nickname));
    }
}
