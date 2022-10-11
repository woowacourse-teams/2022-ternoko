package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.core.dto.response;

import com.woowacourse.ternoko.core.domain.member.crew.Crew;
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

    public static com.woowacourse.ternoko.core.dto.response.CrewResponse from(final Crew crew) {
        return com.woowacourse.ternoko.core.dto.response.CrewResponse.crewResponseBuilder()
                .id(crew.getId())
                .name(crew.getName())
                .nickname(crew.getNickname())
                .email(crew.getEmail())
                .imageUrl(crew.getImageUrl())
                .build();
    }
}
