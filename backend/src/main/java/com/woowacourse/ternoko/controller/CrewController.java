package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.dto.CrewResponse;
import com.woowacourse.ternoko.dto.CrewUpdateRequest;
import com.woowacourse.ternoko.login.aop.CrewOnly;
import com.woowacourse.ternoko.login.domain.AuthenticationPrincipal;
import com.woowacourse.ternoko.service.CrewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crews")
public class CrewController {

    private final CrewService crewService;

    @CrewOnly
    @GetMapping("/me")
    public ResponseEntity<CrewResponse> findCrew(@AuthenticationPrincipal final Long crewId) {
        return ResponseEntity.ok(crewService.findCrew(crewId));
    }

    @CrewOnly
    @PatchMapping("/me")
    public ResponseEntity<Void> updateCrew(@AuthenticationPrincipal final Long crewId,
                                           @RequestBody final CrewUpdateRequest crewUpdateRequest) {
        crewService.partUpdateCrew(crewId, crewUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
