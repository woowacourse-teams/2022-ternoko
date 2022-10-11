package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "crewUpdateBuilder")
public class CrewUpdateRequest {

    private String nickname;
    private String imageUrl;
}
