package com.woowacourse.ternoko.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("COACH")
public class Coach extends Member{
    public Coach(Long id, String nickname, String email, String imageUrl) {
        super(id, nickname, email, imageUrl);
    }
}
