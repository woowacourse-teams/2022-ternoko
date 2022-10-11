package com.woowacourse.ternoko.core.domain.member.coach;

import com.woowacourse.ternoko.core.domain.member.Member;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Getter
@EqualsAndHashCode
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Coach coach = (Coach) o;
        return getId() != null && Objects.equals(getId(), coach.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
