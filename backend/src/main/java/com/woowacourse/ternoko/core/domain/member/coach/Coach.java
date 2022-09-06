package com.woowacourse.ternoko.core.domain.member.coach;

import com.woowacourse.ternoko.core.domain.member.Member;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("COACH")
public class Coach extends Member {

    @Column
    private String introduce;

    public Coach(final Long id,
                 final String name,
                 final String nickname,
                 final String email,
                 final String userId,
                 final String imageUrl,
                 final String introduce) {
        super(id, name, nickname, email, userId, imageUrl, MemberType.COACH);
        this.introduce = introduce;
    }

    public Coach(final String name,
                 final String nickname,
                 final String email,
                 final String userId,
                 final String imageUrl,
                 final String introduce) {
        this(null, name, nickname, email, userId, imageUrl, introduce);
    }

    public Coach(final String name,
                 final String email,
                 final String userId,
                 final String imageUrl) {
        this(null, name, null, email, userId, imageUrl, null);
    }

}
