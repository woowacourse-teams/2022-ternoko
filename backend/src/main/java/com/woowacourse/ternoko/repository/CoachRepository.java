package com.woowacourse.ternoko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.ternoko.domain.Coach;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}
