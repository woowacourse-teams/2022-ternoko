package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.member.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CrewRepository extends JpaRepository<Crew, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Crew c set c.nickname = :nickname, c.imageUrl = :imageUrl where c.id = :crewId")
    void updateNickNameAndImageUrl(final Long crewId, final String nickname, final String imageUrl);
}
