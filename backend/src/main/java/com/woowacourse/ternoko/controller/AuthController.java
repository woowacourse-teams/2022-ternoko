package com.woowacourse.ternoko.controller;

import com.slack.api.Slack;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/api/login")
    public void login(@PathVariable String code) {
        Slack slack = new Slack();

    }
}
