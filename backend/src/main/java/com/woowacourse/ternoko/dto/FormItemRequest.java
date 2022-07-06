package com.woowacourse.ternoko.dto;

import com.woowacourse.ternoko.domain.FormItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FormItemRequest {

    private String question;
    private String answer;

    public FormItem toFormItem() {
        return new FormItem(question, answer);
    }
}
