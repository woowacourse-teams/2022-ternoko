package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.Interview;
import com.woowacourse.ternoko.domain.Member;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findAllByCoach(Member member);
}
