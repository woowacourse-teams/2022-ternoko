package com.woowacourse.ternoko.domain;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Location {

    JAMSIL("잠실"),
    SEOLLEUNG("선릉"),
    ONLINE("온라인");

    private final String value;

    public static Location of(final String location) {
        return Arrays.stream(values())
                .filter(it -> it.value.equals(location))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 장소가 없습니다."));
    }
}
