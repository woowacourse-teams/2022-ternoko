package com.woowacourse.ternoko.service;

import com.slack.api.methods.SlackApiException;
import com.woowacourse.ternoko.controller.AuthController;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class AuthServiceTest2 {

    @Autowired
    private AuthController authController;

    @Test
    public void signUp() throws SlackApiException, IOException {
        authController.login("1234");
    }
}
