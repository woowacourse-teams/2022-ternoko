package com.woowacourse.ternoko.core.domain.interview.formitem;

import static com.woowacourse.ternoko.common.exception.ExceptionType.OVER_LENGTH;

import com.woowacourse.ternoko.common.exception.InterviewInvalidException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Answer {

    private static final int ANSWER_MAX_LENGTH = 1000;

    @Column(length = 1000, name = "answer", nullable = false)
    private String value;

    private Answer(final String value) {
        this.value = value;
    }

    public static Answer from(final String value) {
        if (value.length() > ANSWER_MAX_LENGTH) {
            throw new InterviewInvalidException(OVER_LENGTH, Long.valueOf(ANSWER_MAX_LENGTH));
        }
        return new Answer(value);
    }
}
