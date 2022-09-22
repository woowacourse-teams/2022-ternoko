package com.woowacourse.ternoko.core.dto.response;

import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "formItemBuilder")
public class FormItemResponse {

    private String question;
    private String answer;

    public static FormItemResponse from(FormItem formItem) {
        return FormItemResponse.formItemBuilder()
                .question(formItem.getQuestion().getValue())
                .answer(formItem.getAnswer().getValue())
                .build();
    }
}
