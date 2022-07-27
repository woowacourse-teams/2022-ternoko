package com.woowacourse.ternoko.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    COACH_NOT_FOUND(HttpStatus.BAD_REQUEST, "번째 코치를 찾을 수 없습니다."),
    CREW_NOT_FOUND(HttpStatus.BAD_REQUEST, "번째 크루를 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "번째 면담 예약을 찾을 수 없습니다."),
    INVALID_RESERVATION_DATE(HttpStatus.BAD_REQUEST, "면담 예약은 최소 하루 전에 가능 합니다."),
    INVALID_AVAILABLE_DATE_TIME(HttpStatus.BAD_REQUEST, "선택한 날짜는 해당 코치의 가능한 시간이 아닙니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다.");

    private final HttpStatus statusCode;
    private final String message;
}
