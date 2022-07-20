package com.woowacourse.ternoko.common;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.woowacourse.ternoko.common.exception.ExceptionType;
import com.woowacourse.ternoko.common.exception.ExpiredTokenException;
import com.woowacourse.ternoko.common.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final SecretKey key;
	private final long validityInMilliseconds;

	public JwtProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
		@Value("${security.jwt.token.expire-length}") final long validityInMilliseconds) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		this.validityInMilliseconds = validityInMilliseconds;
	}

	public String createToken(final String payload) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		return Jwts.builder()
			.setSubject(payload)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String getPayload(final String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key).build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public void validateToken(final String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException(ExceptionType.EXPIRED_TOKEN);
		} catch (Exception e) {
			throw new InvalidTokenException(ExceptionType.INVALID_TOKEN);
		}
	}
}
