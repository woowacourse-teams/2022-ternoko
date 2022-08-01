package com.woowacourse.ternoko.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmMessage {

    CREW_CREATE("[예약 완료] %s, %s과의 면담이 %s에 예약되었습니다."),
    CREW_UPDATE("[예약 수정] %s, %s과의 면담 예약이 %s로 수정되었습니다."),
    COACH_CANCEL("[예약 취소] %s, %s과의 면담 예약(%s)이 취소되었습니다."),
    CREW_DELETE("[예약 삭제] %s, %s과의 면담 예약(%s)이 삭제되었습니다.");

    private final String message;
}
