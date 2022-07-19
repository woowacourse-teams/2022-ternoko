package com.woowacourse.ternoko.domain.member;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("COACH")
public class Coach extends Member {

    public Coach(final Long id, final String nickname, final String email, final String imageUrl) {
        super(id, nickname, email, imageUrl);
    }

    public Coach(final String nickname, final String email, final String imageUrl) {
        this(null, nickname, email, imageUrl);
    }
}
