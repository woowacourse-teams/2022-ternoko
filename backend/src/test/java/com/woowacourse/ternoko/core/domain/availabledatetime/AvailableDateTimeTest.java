package com.woowacourse.ternoko.core.domain.availabledatetime;

import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.DELETED;
import static com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// TODO: domain 테스트 추가 필요
class AvailableDateTimeTest {

    @Test
    @DisplayName("DELETED 상태의 면담가능시간은 수정할 수 없다.")
    void changeStatusDeleted() {
        final AvailableDateTime availableDateTime = new AvailableDateTime(1L, LocalDateTime.now(), DELETED);
        assertThatThrownBy(() -> availableDateTime.changeStatus(OPEN));
    }
}