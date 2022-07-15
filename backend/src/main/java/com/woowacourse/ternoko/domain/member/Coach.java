package com.woowacourse.ternoko.domain.member;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("COACH")
public class Coach extends Member{

    private String info;

    public Coach(Long id, String nickname, String email, String imageUrl) {
        super(id, nickname, email, imageUrl);
    }

    public Coach(final Long id, final String nickname, final String email, final String imageUrl, final String info) {
        super(id, nickname, email, imageUrl);
        this.info = info;
    }
}
