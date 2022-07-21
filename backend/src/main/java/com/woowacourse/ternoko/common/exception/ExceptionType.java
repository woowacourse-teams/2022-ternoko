package com.woowacourse.ternoko.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    COACH_NOT_FOUND(400, "번째 코치를 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(400, "번째 면담 예약을 찾을 수 없습니다."),
    INVALID_RESERVATION_DATE(400, "면담 예약은 최소 하루 전에 가능 합니다."),
    INVALID_AVAILABLE_DATE_TIME(400, "선택한 날짜는 해당 코치의 가능한 시간이 아닙니다.");


    private final int code;
    private final String message;
}
