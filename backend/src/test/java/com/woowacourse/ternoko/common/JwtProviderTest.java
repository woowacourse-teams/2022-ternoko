package com.woowacourse.ternoko.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.ternoko.common.exception.ExpiredTokenException;
import com.woowacourse.ternoko.common.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JwtProviderTest {
    private JwtProvider jwtProvider = new JwtProvider(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno",
            3600000);

    @DisplayName("유효한 토큰이 생성된 후 원하는 playload로 변환되는지 검증한다.")
    @Test
    void checkPayloadAfterIssuingToken() {
        final String payload = "\"email\":\"example@example.com\"";
        final String accessToken = jwtProvider.createToken(payload);
        assertThat(jwtProvider.getPayload(accessToken)).isEqualTo(payload);
    }

    @DisplayName("토큰을 검증할 때")
    @Nested
    class TokenValidationTest {

        private JwtProvider invalidJwtTokenProvider = new JwtProvider(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno",
                1);

        @DisplayName("토큰이 유효하다면 에러를 발생시키지 않는다.")
        @Test
        void validToken() {
            final String payload = "\"email\":\"example@example.com\"";
            final String accessToken = jwtProvider.createToken(payload);
            assertThatNoException().isThrownBy(() -> jwtProvider.validateToken(accessToken));
        }

        @DisplayName("토큰이 만료된 토큰이면 에러를 던진다.")
        @Test
        void expiredToken() {
            final String payload = "\"email\":\"example@example.com\"";
            final String accessToken = invalidJwtTokenProvider.createToken(payload);
            assertThatThrownBy(() -> invalidJwtTokenProvider.validateToken(accessToken))
                    .isInstanceOf(ExpiredTokenException.class);
        }

        @DisplayName("토큰이 발급되지 않은 토큰이면 에러를 던진다.")
        @Test
        void invalidToken() {
            final String unissuedAccessToken = "유효하지 않은 토큰";
            assertThatThrownBy(() -> jwtProvider.validateToken(unissuedAccessToken))
                    .isInstanceOf(InvalidTokenException.class);
        }
    }
}
