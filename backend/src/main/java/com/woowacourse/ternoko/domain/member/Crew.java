package com.woowacourse.ternoko.domain.member;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("CREW")
public class Crew extends Member {
    public Crew(final Long id, final String nickname, final String email, final String imageUrl) {
        super(id, nickname, email, imageUrl);
    }
}
