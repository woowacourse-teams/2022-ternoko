package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "loginResponseBuilder")
public class LoginResponse {

    private boolean hasNickname;
    private MemberType memberRole;
    private String accessToken;

    public static LoginResponse of(final MemberType memberRole, final String accessToken, final boolean hasNickname) {
        return LoginResponse.loginResponseBuilder()
                .memberRole(memberRole)
                .accessToken(accessToken)
                .hasNickname(hasNickname)
                .build();
    }
}
