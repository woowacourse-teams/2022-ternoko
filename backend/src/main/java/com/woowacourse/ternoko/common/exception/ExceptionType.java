package com.woowacourse.ternoko.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {

	COACH_NOT_FOUND(400, "번째 코치를 찾을 수 없습니다."),
	RESERVATION_NOT_FOUND(400, "번째 면담 예약을 찾을 수 없습니다."),
	INVALID_RESERVATION_DATE(400, "면담 예약은 최소 하루 전에 가능 합니다."),
	INVALID_TOKEN(401, "유효하지 않은 토큰입니다"),
	EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
	UNAUTHORIZED_MEMBER(401, "토큰이 존재하지 않습니다.");

	private final int code;
	private final String message;
}
