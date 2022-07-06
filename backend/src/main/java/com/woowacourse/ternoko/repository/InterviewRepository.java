package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
