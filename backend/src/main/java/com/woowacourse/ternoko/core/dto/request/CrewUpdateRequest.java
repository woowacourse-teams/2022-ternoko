package com.woowacourse.ternoko.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrewUpdateRequest {

    private String nickname;
    private String imageUrl;
}
