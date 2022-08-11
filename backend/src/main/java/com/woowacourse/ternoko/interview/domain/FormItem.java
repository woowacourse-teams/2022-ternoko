package com.woowacourse.ternoko.interview.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FormItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    public FormItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public FormItem(Long id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public FormItem(String question, String answer, Interview interview) {
        this.question = question;
        this.answer = answer;
        this.interview = interview;
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