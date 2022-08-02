package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.member.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Coach c set c.nickname = :nickname, c.imageUrl = :imageUrl, c.introduce = :introduce where c.id = :coachId")
    void updateNickNameAndImageUrlAndIntroduce(final Long coachId, final String nickname, final String imageUrl,
                                               final String introduce);
}
