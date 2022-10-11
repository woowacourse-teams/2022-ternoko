package com.woowacourse.ternoko.core.domain.member.crew;

import com.woowacourse.ternoko.core.domain.member.Member;
import com.woowacourse.ternoko.core.domain.member.MemberType;
import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@EqualsAndHashCode
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Crew crew = (Crew) o;
        return getId() != null && Objects.equals(getId(), crew.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
