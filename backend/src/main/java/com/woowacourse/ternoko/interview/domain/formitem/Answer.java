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
public class Answer {

    private static final int ANSWER_MAX_LENGTH = 1000;

    @Column(length = 1000, name = "answer", nullable = false)
    private String value;

    private Answer(final String value) {
        this.value = value;
    }

    public static Answer of(final String value) {
        if (value.length() > ANSWER_MAX_LENGTH) {
            throw new InvalidLengthException(OVER_LENGTH, ANSWER_MAX_LENGTH);
        }
        return new Answer(value);
    }
}
