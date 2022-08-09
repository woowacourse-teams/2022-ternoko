package com.woowacourse.ternoko.domain.formItem;

import com.woowacourse.ternoko.domain.Interview;
import javax.persistence.Embedded;
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

    @Embedded
    private Question question;

    @Embedded
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    private FormItem(final Question question, final Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public static FormItem from(String question, String answer) {
        return new FormItem(Question.of(question), Answer.of(answer));
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FormItem formItem = (FormItem) o;

        if (getId() != null ? !getId().equals(formItem.getId()) : formItem.getId() != null) {
            return false;
        }
        if (getQuestion() != null ? !getQuestion().equals(formItem.getQuestion()) : formItem.getQuestion() != null) {
            return false;
        }
        if (getAnswer() != null ? !getAnswer().equals(formItem.getAnswer()) : formItem.getAnswer() != null) {
            return false;
        }
        return getInterview() != null ? getInterview().equals(formItem.getInterview())
                : formItem.getInterview() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getQuestion() != null ? getQuestion().hashCode() : 0);
        result = 31 * result + (getAnswer() != null ? getAnswer().hashCode() : 0);
        result = 31 * result + (getInterview() != null ? getInterview().hashCode() : 0);
        return result;
    }
}
