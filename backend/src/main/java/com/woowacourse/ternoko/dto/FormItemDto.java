package com.woowacourse.ternoko.interview.dto;

import com.woowacourse.ternoko.interview.domain.FormItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "formItemBuilder")
public class FormItemDto {

    private String question;
    private String answer;

    public static FormItemDto from(FormItem formItem) {
        return FormItemDto.formItemBuilder()
                .question(formItem.getQuestion())
                .answer(formItem.getAnswer())
                .build();
    }

    public FormItem toFormItem() {
        return new FormItem(question, answer);
    }
}
