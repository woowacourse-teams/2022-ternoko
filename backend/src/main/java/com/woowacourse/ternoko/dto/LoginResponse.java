package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "loginResponseBuilder")
public class LoginResponse {

    private boolean isSignup;
    private Type memberRole;
    private String accessToken;

    public static LoginResponse of(final Type memberRole, final String accessToken, final boolean isSignup) {
        return LoginResponse.loginResponseBuilder()
                .memberRole(memberRole)
                .accessToken(accessToken)
                .isSignup(isSignup)
                .build();
    }
}
