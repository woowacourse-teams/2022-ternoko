package com.woowacourse.ternoko.domain.member;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("CREW")
public class Crew extends Member {

    public Crew(final Long id,
                final String name,
                final String nickname,
                final String email,
                final String userId,
                final String imageUrl) {
        super(id, name, nickname, email, userId, imageUrl, MemberType.CREW);
    }

    public Crew(final String name,
                final String nickname,
                final String email,
                final String userId,
                final String imageUrl) {
        this(null, name, nickname, email, userId, imageUrl);
    }

    public Crew(final String name,
                final String email,
                final String userId,
                final String imageUrl) {
        this(null, name, null, email, userId, imageUrl);
    }
}
