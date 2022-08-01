package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.member.Crew;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "crewResponseBuilder")
public class CrewResponse {

    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String imageUrl;

    public static CrewResponse from(final Crew crew) {
        return CrewResponse.crewResponseBuilder()
                .id(crew.getId())
                .name(crew.getName())
                .nickname(crew.getNickname())
                .email(crew.getEmail())
                .imageUrl(crew.getImageUrl())
                .build();
    }
}
