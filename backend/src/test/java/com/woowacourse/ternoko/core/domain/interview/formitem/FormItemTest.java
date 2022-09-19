package com.woowacourse.ternoko.core.domain.interview.formitem;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FormItemTest {

    @DisplayName("면담 사전 질문 업데이트 정상 수행 ")
    @Test
    void update_formItem() {
        // given
        final FormItem formItem = FormItem.of("테스트 질문", "테스트 답변");
        final FormItem updateFormItem = FormItem.of("업데이트 테스트 질문", "업데이트 테스트 답변");

        // when
        formItem.update(updateFormItem);

        // then
        assertThat(formItem).isEqualTo(updateFormItem);
    }
}
