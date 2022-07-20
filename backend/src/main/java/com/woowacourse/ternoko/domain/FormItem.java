package com.woowacourse.ternoko.domain;

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

    public FormItem(String question, String answer, Interview interview) {
        this.question = question;
        this.answer = answer;
        this.interview = interview;
    }

    public void addInterview(Interview interview) {
        this.interview = interview;
    }
}
