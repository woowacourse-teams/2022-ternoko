package com.woowacourse.ternoko.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    COACH_NOT_FOUND(HttpStatus.BAD_REQUEST, "번째 코치를 찾을 수 없습니다."),
    CREW_NOT_FOUND(HttpStatus.BAD_REQUEST, "번째 크루를 찾을 수 없습니다."),
    INTERVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "번째 면담 예약을 찾을 수 없습니다."),
    INVALID_INTERVIEW_CREW_ID(HttpStatus.BAD_REQUEST, "다른 크루의 예약에 접근할 수 없습니다."),
    INVALID_INTERVIEW_COACH_ID(HttpStatus.BAD_REQUEST, "다른 코치의 예약에 접근할 수 없습니다."),
    INVALID_INTERVIEW_MEMBER_ID(HttpStatus.BAD_REQUEST, "해당 면담에 해당하지 않는 회원은 접근할 수 없습니다."),
    INVALID_INTERVIEW_DATE(HttpStatus.BAD_REQUEST, "면담 예약은 최소 하루 전에 가능 합니다."),
    INVALID_INTERVIEW_DUPLICATE_DATE_TIME(HttpStatus.BAD_REQUEST, "면담 예약은 같은 시간, 같은 코치에 단 한번 가능합니다."),
    INVALID_AVAILABLE_DATE_TIME(HttpStatus.BAD_REQUEST, "선택한 날짜는 해당 코치의 가능한 시간이 아닙니다."),
    OVER_LENGTH(HttpStatus.BAD_REQUEST, "자를 넘을 수 없습니다."),
    INVALID_STATUS_CREATE_COMMENT(HttpStatus.BAD_REQUEST, "해당 면담은 자유로운 한마디를 입력할 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    CANNOT_EDIT_INTERVIEW(HttpStatus.BAD_REQUEST, "이미 사전메일을 보낸 면담은 수정할 수 없습니다.");

    private final HttpStatus statusCode;
    private final String message;
}
