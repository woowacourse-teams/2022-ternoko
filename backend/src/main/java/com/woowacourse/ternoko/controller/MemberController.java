package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.dto.CoachesResponse;
import com.woowacourse.ternoko.service.CoachService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final CoachService coachService;

    @GetMapping("/reservations/coaches")
    public ResponseEntity<CoachesResponse> findCoaches() {
        return ResponseEntity.ok(coachService.findCoaches());
    }
}
