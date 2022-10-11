package com.woowacourse.ternoko.core.domain.interview.formitem;

import static com.woowacourse.ternoko.common.exception.ExceptionType.OVER_LENGTH;

import com.woowacourse.ternoko.common.exception.exception.InterviewInvalidException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Question {

    private static final int QUESTION_MAX_LENGTH = 255;

    @Column(name = "question", nullable = false)
    private String value;

    private Question(final String value) {
        this.value = value;
    }

    public static Question from(final String value) {
        if (value.length() > QUESTION_MAX_LENGTH) {
            throw new InterviewInvalidException(OVER_LENGTH, Long.valueOf(QUESTION_MAX_LENGTH));
        }
        return new Question(value);
    }
}
