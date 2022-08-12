package com.woowacourse.ternoko.controller;

import com.slack.api.methods.SlackApiException;
import com.woowacourse.ternoko.config.AuthenticationPrincipal;
import com.woowacourse.ternoko.dto.LoginResponse;
import com.woowacourse.ternoko.service.AuthService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/login")
public class AuthController {

    private final AuthService authService;

    @GetMapping
    public ResponseEntity<LoginResponse> login(@RequestParam final String code, @RequestParam final String redirectUrl)
            throws SlackApiException, IOException {
        final LoginResponse loginResponse = authService.login(code, redirectUrl);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/valid")
    public ResponseEntity<Void> checkValidAccessTokenAndRole(@AuthenticationPrincipal final Long id,
                                                             @RequestParam final String type) {
        authService.checkMemberType(id, type);
        return ResponseEntity.ok().build();
    }
}
