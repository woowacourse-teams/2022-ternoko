package com.woowacourse.ternoko.dto.request;

import com.woowacourse.ternoko.domain.FormItem;
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
                .question(formItem.getQuestion())
                .answer(formItem.getAnswer())
                .build();
    }

    public FormItem toFormItem() {
        return new FormItem(question, answer);
    }
}
