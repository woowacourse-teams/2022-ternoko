package com.woowacourse.ternoko.core.domain.comment.core.domain.interview.formitem;

import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.formitem.Answer;
import com.woowacourse.ternoko.core.domain.interview.formitem.Question;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
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

    public static com.woowacourse.ternoko.core.domain.interview.formitem.FormItem of(String question, String answer) {
        return new com.woowacourse.ternoko.core.domain.interview.formitem.FormItem(Question.from(question), Answer.from(answer));
    }

    public void addInterview(final Interview interview) {
        this.interview = interview;
    }

    public void update(com.woowacourse.ternoko.core.domain.interview.formitem.FormItem formItem) {
        this.question = formItem.question;
        this.answer = formItem.answer;
    }
}
