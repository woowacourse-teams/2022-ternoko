package com.woowacourse.ternoko.core.domain.interview.formitem;

import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEMS1;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.FORM_ITEMS2;
import static com.woowacourse.ternoko.support.fixture.InterviewFixture.INTERVIEW;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FormItemsTest {

    @DisplayName("면담 사전 질문 List 업데이트 정상 수행 확인")
    @Test
    void update_formItems() {
        // given
        final Interview interview = INTERVIEW.copyOf();
        final FormItems formItems = new FormItems(FORM_ITEMS1, interview);
        final List<FormItem> updateFormItemList = FORM_ITEMS2;

        formItems.update(updateFormItemList);

        // then
        assertThat(formItems.getFormItems().equals(updateFormItemList));
    }
}
