package com.woowacourse.ternoko.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    // 인증 관련 Exception
    UNAUTHORIZED_MEMBER(1001, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN(1002, "유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN(1003, "토큰이 만료되었습니다."),

    // Coach 관련 Exception
    COACH_NOT_FOUND(2001, "번째 코치를 찾을 수 없습니다."),
    INVALID_INTERVIEW_COACH_ID(2002, "다른 코치의 예약에 접근할 수 없습니다."),
    INVALID_AVAILABLE_DATE_TIME(2003, "선택한 날짜는 해당 코치의 가능한 시간이 아닙니다."),

    // Crew 관련 Exception,
    CREW_NOT_FOUND(3001, "번째 크루를 찾을 수 없습니다."),
    INVALID_INTERVIEW_CREW_ID(3002, "다른 크루의 예약에 접근할 수 없습니다."),

    // Interview 관련 Exception,
    INTERVIEW_NOT_FOUND(4001, "번째 면담 예약을 찾을 수 없습니다."),
    INVALID_INTERVIEW_DATE(4002, "면담 예약은 최소 하루 전에 가능 합니다."),
    INVALID_INTERVIEW_DUPLICATE_DATE_TIME(4003, "면담 예약은 같은 시간, 같은 코치에 단 한번 가능합니다."),
    OVER_LENGTH(4004, "자를 넘을 수 없습니다."),
    CANNOT_EDIT_INTERVIEW(4005, "이미 사전메일을 보낸 면담은 수정할 수 없습니다."),

    // Comment 관련 Exception
    INVALID_STATUS_CREATE_COMMENT(5001, "해당 면담은 자유로운 한마디를 입력할 수 없습니다."),
    INVALID_STATUS_FIND_COMMENT(5002, "해당 면담은 자유로운 한마디를 조회할 수 없습니다."),
    COMMENT_NOT_FOUND(5003, "번째 코멘트를 찾을 수 없습니다."),
    INVALID_INTERVIEW_MEMBER_ID(5004, "해당 면담에 해당하지 않는 회원은 접근할 수 없습니다."),
    INVALID_COMMENT_MEMBER_ID(5005, "해당 코멘트에 해당하지 않는 회원은 접근할 수 없습니다."),
    INVALID_COMMENT_INTERVIEW_ID(5006, "코멘트에 해당하지 않는 면담입니다."),

    // HTTP 관련 Exception
    REQUEST_EXCEPTION(8001, "http 요청 에러입니다."),

    // 알수 없는 Exception
    UNHANDLED_EXCEPTION(9999, "예상치 못한 예외입니다.");

    private final int statusCode;
    private final String message;
}
