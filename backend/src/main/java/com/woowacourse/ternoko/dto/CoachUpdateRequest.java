package com.woowacourse.ternoko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "coachUpdateBuilder")
public class CoachUpdateRequest {

    private String nickname;
    private String imageUrl;
    private String introduce;
}
