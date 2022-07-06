package com.woowacourse.ternoko.repository;

import com.woowacourse.ternoko.domain.FormItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormItemRepository extends JpaRepository<FormItem, Long> {
}
