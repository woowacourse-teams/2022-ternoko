package com.woowacourse.ternoko.core.domain.member.coach;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoachRepository extends JpaRepository<Coach, Long> {

//
//    @Modifying(clearAutomatically = true)
//    @Query("SELECT id, name, nickname, email, imageUrl, introduce, hasOpenTime FROM coach as c ORDER BY id  asc LIMIT 10")
//    void findAllLimit10();

    Page<Coach> findAll(Pageable pageable);

//

//    List<Coach> findTop10();

    @Modifying(clearAutomatically = true)
    @Query("update Coach c set c.nickname = :nickname, c.imageUrl = :imageUrl, c.introduce = :introduce where c.id = :coachId")
    void updateNickNameAndImageUrlAndIntroduce(@Param("coachId") final Long coachId,
                                               @Param("nickname") final String nickname,
                                               @Param("imageUrl") final String imageUrl,
                                               @Param("introduce") final String introduce);
}
