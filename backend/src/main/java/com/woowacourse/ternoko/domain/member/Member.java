package com.woowacourse.ternoko.domain.member;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String userId;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private MemberType memberType;

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }
}
