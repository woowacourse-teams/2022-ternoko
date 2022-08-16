package com.woowacourse.ternoko.common.log;

public class LogForm {

    private static final String NEWLINE = System.lineSeparator();

    public static final String SUCCESS_LOGGING_FORM =
            NEWLINE + "HTTP Method : {} "
            + NEWLINE + "Request URI : {} "
            + NEWLINE + "AccessToken is exist : {} "
            + NEWLINE + "Request Body : {}";

    public static final String FAILED_LOGGING_FORM =
            SUCCESS_LOGGING_FORM
            + NEWLINE + "CODE : {} "
            + NEWLINE + "MESSAGE : {}";
}
