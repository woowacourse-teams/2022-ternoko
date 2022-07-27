package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Member;
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
    private String name;
    private String nickname;
    private String email;
    private String imageUrl;
    private String introduce;

    public static CoachResponse from(final Coach coach) {
        return coachResponseBuilder()
                .id(coach.getId())
                .name(coach.getName())
                .nickname(coach.getNickname())
                .imageUrl(coach.getImageUrl())
                .introduce(coach.getIntroduce())
                .build();
    }
}
