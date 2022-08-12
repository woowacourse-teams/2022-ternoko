package com.woowacourse.ternoko.interview.domain.formitem;

import static com.woowacourse.ternoko.common.exception.ExceptionType.OVER_LENGTH;

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

    @Column(length = 1000, nullable = false)
    private String answer;

    private Answer(final String answer) {
        this.answer = answer;
    }

    public static Answer of(final String answer) {
        if (answer.length() > ANSWER_MAX_LENGTH) {
            throw new InvalidLengthException(OVER_LENGTH, ANSWER_MAX_LENGTH);
        }
        return new Answer(answer);
    }
}
