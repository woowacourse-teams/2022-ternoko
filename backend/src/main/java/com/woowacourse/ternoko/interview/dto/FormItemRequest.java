package com.woowacourse.ternoko.interview.dto;

import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "formItemBuilder")
public class FormItemRequest {

    private String question;
    private String answer;

    public static FormItemRequest from(FormItem formItem) {
        return FormItemRequest.formItemBuilder()
                .question(formItem.getQuestion().getValue())
                .answer(formItem.getAnswer().getValue())
                .build();
    }

    public FormItem toFormItem() {
        return FormItem.from(question, answer);
    }
}
