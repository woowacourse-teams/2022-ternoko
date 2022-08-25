package com.woowacourse.ternoko.interview.domain.formitem;

import static com.woowacourse.ternoko.common.exception.type.ExceptionType.OVER_LENGTH;

import com.woowacourse.ternoko.common.exception.InvalidLengthException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Question {

    private static final int QUESTION_MAX_LENGTH = 255;

    @Column(name = "question", nullable = false)
    private String value;

    private Question(final String value) {
        this.value = value;
    }

    public static Question from(final String value) {
        if (value.length() > QUESTION_MAX_LENGTH) {
            throw new InvalidLengthException(OVER_LENGTH, QUESTION_MAX_LENGTH);
        }
        return new Question(value);
    }
}
