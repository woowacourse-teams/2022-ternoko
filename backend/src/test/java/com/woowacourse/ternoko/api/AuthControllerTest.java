package com.woowacourse.ternoko.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.ternoko.domain.Type;
import com.woowacourse.ternoko.dto.LoginResponse;
import com.woowacourse.ternoko.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest extends ControllerTest {

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("코치/크루 - 로그인을 한다.")
    void login() throws Exception {
        // given, when
        when(authService.login(any()))
                .thenReturn(LoginResponse.of(Type.CREW, "sampleToken", false));

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/login")
                        .queryParam("code", "slackCode"))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
