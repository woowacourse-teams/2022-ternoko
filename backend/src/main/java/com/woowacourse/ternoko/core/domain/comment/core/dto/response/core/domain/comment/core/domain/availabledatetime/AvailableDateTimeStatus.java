package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.core.domain.availabledatetime;

public enum AvailableDateTimeStatus {

    OPEN,
    USED;

    public boolean matchType(final com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.availabledatetime.AvailableDateTimeStatus availableDateTimeStatus) {
        return this.equals(availableDateTimeStatus);
    }

    public com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.availabledatetime.AvailableDateTimeStatus change() {
        if (isUsed()) {
            return OPEN;
        }
        return USED;
    }

    private boolean isUsed() {
        return this.equals(USED);
    }
}
