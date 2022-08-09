package com.woowacourse.ternoko.interview.domain;

import static com.woowacourse.ternoko.common.exception.ExceptionType.*;

import com.woowacourse.ternoko.common.exception.InvalidLengthException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class FormItem {

    private static final int QUESTION_MAX_LENGTH = 255;
    private static final int ANSWER_MAX_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(length = 1000, nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    private FormItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public static FormItem from(String question, String answer) {
        validateLength(question, answer);
        return new FormItem(question, answer);
    }

    private static void validateLength(final String question, final String answer) {
        if (question.length() > QUESTION_MAX_LENGTH) {
            throw new InvalidLengthException(OVER_LENGTH, QUESTION_MAX_LENGTH);
        }

        if (answer.length() > ANSWER_MAX_LENGTH) {
            throw new InvalidLengthException(OVER_LENGTH, ANSWER_MAX_LENGTH);
        }
    }

    public void addInterview(final Interview interview) {
        this.interview = interview;
        interview.getFormItems().add(this);
    }

    public void update(FormItem formItem, Interview interview) {
        this.question = formItem.question;
        this.answer = formItem.answer;
        this.interview = interview;
    }
}
