package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.domain.comment.core.domain.member.coach;

import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoachRepository extends JpaRepository<com.woowacourse.ternoko.core.domain.comment.core.domain.member.coach.Coach, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Coach c set c.nickname = :nickname, c.imageUrl = :imageUrl, c.introduce = :introduce where c.id = :coachId")
    void updateNickNameAndImageUrlAndIntroduce(@Param("coachId") final Long coachId,
                                               @Param("nickname") final String nickname,
                                               @Param("imageUrl") final String imageUrl,
                                               @Param("introduce") final String introduce);
}
