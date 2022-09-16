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

    @GetMapping("/test")
    public String test(){
        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>Title</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<a href=\"https://slack.com/openid/connect/authorize?scope=openid%20profile%20email&amp;response_type=code&amp;redirect_uri=https%3A%2F%2Flocalhost%3A8080%2Fapi%2Flogin&amp;client_id=524707623267.4042285006293\"\n"
                + "   style=\"align-items:center;color:#000;background-color:#fff;border:1px solid #ddd;border-radius:4px;display:inline-flex;font-family:Lato, sans-serif;font-size:16px;font-weight:600;height:48px;justify-content:center;text-decoration:none;width:256px\">\n"
                + "    <svg xmlns=\"http://www.w3.org/2000/svg\" style=\"height:20px;width:20px;margin-right:12px\" viewBox=\"0 0 122.8 122.8\">\n"
                + "        <path d=\"M25.8 77.6c0 7.1-5.8 12.9-12.9 12.9S0 84.7 0 77.6s5.8-12.9 12.9-12.9h12.9v12.9zm6.5 0c0-7.1 5.8-12.9 12.9-12.9s12.9 5.8 12.9 12.9v32.3c0 7.1-5.8 12.9-12.9 12.9s-12.9-5.8-12.9-12.9V77.6z\"\n"
                + "              fill=\"#e01e5a\"></path>\n"
                + "        <path d=\"M45.2 25.8c-7.1 0-12.9-5.8-12.9-12.9S38.1 0 45.2 0s12.9 5.8 12.9 12.9v12.9H45.2zm0 6.5c7.1 0 12.9 5.8 12.9 12.9s-5.8 12.9-12.9 12.9H12.9C5.8 58.1 0 52.3 0 45.2s5.8-12.9 12.9-12.9h32.3z\"\n"
                + "              fill=\"#36c5f0\"></path>\n"
                + "        <path d=\"M97 45.2c0-7.1 5.8-12.9 12.9-12.9s12.9 5.8 12.9 12.9-5.8 12.9-12.9 12.9H97V45.2zm-6.5 0c0 7.1-5.8 12.9-12.9 12.9s-12.9-5.8-12.9-12.9V12.9C64.7 5.8 70.5 0 77.6 0s12.9 5.8 12.9 12.9v32.3z\"\n"
                + "              fill=\"#2eb67d\"></path>\n"
                + "        <path d=\"M77.6 97c7.1 0 12.9 5.8 12.9 12.9s-5.8 12.9-12.9 12.9-12.9-5.8-12.9-12.9V97h12.9zm0-6.5c-7.1 0-12.9-5.8-12.9-12.9s5.8-12.9 12.9-12.9h32.3c7.1 0 12.9 5.8 12.9 12.9s-5.8 12.9-12.9 12.9H77.6z\"\n"
                + "              fill=\"#ecb22e\"></path>\n"
                + "    </svg>\n"
                + "    Sign in with Slack</a>\n"
                + "</body>\n"
                + "</html>\n";
    }
    @GetMapping
    public ResponseEntity<LoginResponse> login(@RequestParam final String code, @RequestParam final String redirectUrl)
            throws SlackApiException, IOException {
        final LoginResponse loginResponse = authService.login(code, redirectUrl);
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
