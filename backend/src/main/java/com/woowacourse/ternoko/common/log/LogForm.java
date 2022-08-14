package com.woowacourse.ternoko.common.log;

public class LogForm {

    public static final String SUCCESS_LOGGING_FORM =
            "\n HTTP Method : {} " +
            "\n Request URI : {} " +
            "\n AccessToken is exist : {} " +
            "\n Request Body : {}";

    public static final String FAILED_LOGGING_FORM =
            SUCCESS_LOGGING_FORM +
            "\n CODE : {} " +
            "\n MESSAGE : {}";
}
