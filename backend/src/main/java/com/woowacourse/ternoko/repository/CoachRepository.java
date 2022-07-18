package com.woowacourse.ternoko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.ternoko.domain.member.Coach;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}
