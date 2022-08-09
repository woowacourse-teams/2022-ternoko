package com.woowacourse.ternoko.interview.domain;

import com.woowacourse.ternoko.domain.formItem.FormItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormItemRepository extends JpaRepository<FormItem, Long> {
}
