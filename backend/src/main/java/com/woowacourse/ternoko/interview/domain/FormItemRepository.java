package com.woowacourse.ternoko.interview.domain;

import com.woowacourse.ternoko.domain.formitem.FormItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormItemRepository extends JpaRepository<FormItem, Long> {
}
