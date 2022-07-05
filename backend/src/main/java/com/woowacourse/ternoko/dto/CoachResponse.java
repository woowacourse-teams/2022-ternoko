package com.woowacourse.ternoko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(builderMethodName = "coachResponseBuilder")
public class CoachResponse {

    private final Long id;
    private final String nickname;
    private final String imageUrl;
}
