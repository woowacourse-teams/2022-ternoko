package com.woowacourse.ternoko.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MailDto {
    private String from;
    private String to;
    private String subject;
    private String text;

}
