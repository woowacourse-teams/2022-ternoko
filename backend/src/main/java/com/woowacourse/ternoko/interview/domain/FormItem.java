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

    private static final int MAX_LENGNTH = 1000;
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
        validateLength(answer);
        return new FormItem(question, answer);
    }

    private static void validateLength(final String answer) {
        if (answer.length() > MAX_LENGNTH) {
            throw new InvalidLengthException(OVER_LENGTH, MAX_LENGNTH);
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
