package com.woowacourse.ternoko.core.dto.request;

import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormItemRequest {

    private String question;
    private String answer;

    public static FormItemRequest from(FormItem formItem) {
        return FormItemRequest.builder()
                .question(formItem.getQuestion().getValue())
                .answer(formItem.getAnswer().getValue())
                .build();
    }

    public FormItem toFormItem() {
        return FormItem.of(question, answer);
    }
}
