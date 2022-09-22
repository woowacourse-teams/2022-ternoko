package com.woowacourse.ternoko.core.domain.interview.formitem;

import com.woowacourse.ternoko.core.domain.interview.Interview;
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

    public FormItem(final Long id, final Question question, final Answer answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    private FormItem(final Question question, final Answer answer) {
        this(null, question, answer);
    }

    public static FormItem of(String question, String answer) {
        return new FormItem(Question.from(question), Answer.from(answer));
    }

    public void addInterview(final Interview interview) {
        this.interview = interview;
    }

    public void update(FormItem formItem) {
        this.question = formItem.question;
        this.answer = formItem.answer;
    }
}
