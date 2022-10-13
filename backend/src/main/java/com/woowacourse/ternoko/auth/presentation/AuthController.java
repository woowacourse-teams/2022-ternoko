package com.woowacourse.ternoko.auth.presentation;

import com.slack.api.methods.SlackApiException;
import com.woowacourse.ternoko.auth.application.AuthService;
import com.woowacourse.ternoko.auth.dto.response.LoginResponse;
import com.woowacourse.ternoko.auth.presentation.annotation.AuthenticationPrincipal;
import java.io.IOException;
import java.util.Locale;
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

    private static final String ALL = "ALL";
    private final AuthService authService;

    @GetMapping("/coach")
    public ResponseEntity<LoginResponse> loginCoach()
            throws SlackApiException, IOException {
        final LoginResponse loginResponse = authService.loginCoach();
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/crew")
    public ResponseEntity<LoginResponse> loginCrew()
            throws SlackApiException, IOException {
        final LoginResponse loginResponse = authService.loginCrew();
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/valid")
    public ResponseEntity<Void> checkValidAccessTokenAndRole(@AuthenticationPrincipal final Long id,
                                                             @RequestParam final String type) {
        if (type.toUpperCase(Locale.ROOT).equals(ALL)) {
            return ResponseEntity.ok().build();
        }
        authService.checkMemberType(id, type);
        return ResponseEntity.ok().build();
    }
}
