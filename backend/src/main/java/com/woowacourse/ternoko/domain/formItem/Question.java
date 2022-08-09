package com.woowacourse.ternoko.domain.formItem;

import static com.woowacourse.ternoko.common.exception.ExceptionType.OVER_LENGTH;

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

    @Column(nullable = false)
    private String question;

    private Question(final String question) {
        this.question = question;
    }

    public static Question of(final String question) {
        if (question.length() > QUESTION_MAX_LENGTH) {
            throw new InvalidLengthException(OVER_LENGTH, QUESTION_MAX_LENGTH);
        }
        return new Question(question);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Question question1 = (Question) o;

        return getQuestion() != null ? getQuestion().equals(question1.getQuestion()) : question1.getQuestion() == null;
    }

    @Override
    public int hashCode() {
        return getQuestion() != null ? getQuestion().hashCode() : 0;
    }
}
