package com.woowacourse.ternoko.core.domain.comment.core.dto.response.core.dto.response;

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

    public static com.woowacourse.ternoko.core.dto.response.FormItemResponse from(FormItem formItem) {
        return com.woowacourse.ternoko.core.dto.response.FormItemResponse.formItemBuilder()
                .question(formItem.getQuestion().getValue())
                .answer(formItem.getAnswer().getValue())
                .build();
    }
}
