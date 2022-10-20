package com.woowacourse.ternoko.core.domain.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    @Query("SELECT COUNT (m.id) > 0 FROM Member m WHERE m.nickname = :nickname AND m.id <> :memberId")
    boolean existsByIdAndNicknameExceptMe(@Param("memberId") final Long memberId,
                                          @Param("nickname") final String nickname);
}
