package com.woowacourse.ternoko.auth.dto.response;

import com.woowacourse.ternoko.core.domain.member.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private boolean hasNickname;
    private MemberType memberRole;
    private String accessToken;

    public static LoginResponse of(final MemberType memberRole, final String accessToken, final boolean hasNickname) {
        return LoginResponse.builder()
                .memberRole(memberRole)
                .accessToken(accessToken)
                .hasNickname(hasNickname)
                .build();
    }
}
