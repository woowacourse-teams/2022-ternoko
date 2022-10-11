package com.woowacourse.ternoko.core.domain.comment.core.domain.member;

import com.woowacourse.ternoko.core.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);
}
