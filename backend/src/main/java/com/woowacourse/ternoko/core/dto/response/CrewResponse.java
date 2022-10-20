package com.woowacourse.ternoko.core.dto.response;

import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrewResponse {

    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String imageUrl;

    public static CrewResponse from(final Crew crew) {
        return CrewResponse.builder()
                .id(crew.getId())
                .name(crew.getName())
                .nickname(crew.getNickname())
                .email(crew.getEmail())
                .imageUrl(crew.getImageUrl())
                .build();
    }
}
