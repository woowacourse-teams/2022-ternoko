package com.woowacourse.ternoko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.ternoko.domain.Crew;

public interface CrewRepository extends JpaRepository<Crew, Long> {
}
