package com.woowacourse.ternoko.controller;

import com.woowacourse.ternoko.config.AuthenticationPrincipal;
import com.woowacourse.ternoko.dto.request.CrewUpdateRequest;
import com.woowacourse.ternoko.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crews")
public class CrewController {

    private final CrewService crewService;

    @PatchMapping("/me")
    public ResponseEntity<Void> updateCrew(@AuthenticationPrincipal final Long crewId,
                                           @RequestBody final CrewUpdateRequest crewUpdateRequest) {
        crewService.partUpdateCrew(crewId, crewUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
