package com.woowacourse.ternoko.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slack.api.methods.SlackApiException;
import com.woowacourse.ternoko.dto.TokenResponse;
import com.woowacourse.ternoko.service.AuthService;

@RestController
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/api/login")
	public ResponseEntity<TokenResponse> login(@RequestParam final String code) throws SlackApiException, IOException {
		final String accessToken = authService.login(code);
		return ResponseEntity.ok().body(new TokenResponse(accessToken));
	}
}
