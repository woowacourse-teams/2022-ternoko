package com.woowacourse.ternoko.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailSenderDto {

    private String from;
    private String to;
    private String subject;
    private String text;
}
