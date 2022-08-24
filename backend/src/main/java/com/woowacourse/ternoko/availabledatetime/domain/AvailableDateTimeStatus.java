package com.woowacourse.ternoko.availabledatetime.domain;

public enum AvailableDateTimeStatus {
    OPEN,
    USED;

    public boolean matchType(final AvailableDateTimeStatus availableDateTimeStatus) {
        return this.equals(availableDateTimeStatus);
    }
}
