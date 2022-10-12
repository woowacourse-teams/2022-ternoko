package com.woowacourse.ternoko.core.dto.response;

import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachResponse {

    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String imageUrl;
    private String introduce;
    private boolean hasOpenTime;

    public static CoachResponse from(final Coach coach) {
        return builder()
                .id(coach.getId())
                .name(coach.getName())
                .email(coach.getEmail())
                .nickname(coach.getNickname())
                .imageUrl(coach.getImageUrl())
                .introduce(coach.getIntroduce())
                .build();
    }

    public static CoachResponse of(final Coach coach, final Long count) {
        return builder()
                .id(coach.getId())
                .name(coach.getName())
                .email(coach.getEmail())
                .nickname(coach.getNickname())
                .imageUrl(coach.getImageUrl())
                .introduce(coach.getIntroduce())
                .hasOpenTime(count > 0)
                .build();
    }
}
