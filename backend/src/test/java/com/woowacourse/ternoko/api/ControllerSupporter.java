package com.woowacourse.ternoko.api;

import com.woowacourse.ternoko.api.restdocs.RestDocsTestSupporter;
import com.woowacourse.ternoko.login.application.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class ControllerSupporter extends RestDocsTestSupporter {

    @Autowired
    public JwtProvider jwtProvider;

}
