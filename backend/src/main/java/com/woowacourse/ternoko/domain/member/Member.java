package com.woowacourse.ternoko.domain.member;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.ternoko.domain.MemberType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "ROLE")
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

    @Enumerated
    @Column(name = "ROLE")
    private MemberType memberType;

    public boolean sameMember(Long id) {
        return this.id.equals(id);
    }
}
