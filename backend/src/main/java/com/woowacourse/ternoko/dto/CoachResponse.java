package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "coachResponseBuilder")
public class CoachResponse {

    private Long id;
    private String nickname;
    private String imageUrl;

    public static CoachResponse from(final Member member) {
        return coachResponseBuilder()
                .id(member.getId())
                .imageUrl(member.getImageUrl())
                .nickname(member.getNickname())
                .build();
    }
}
