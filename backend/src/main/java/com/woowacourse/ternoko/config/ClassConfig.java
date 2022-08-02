package com.woowacourse.ternoko.config;

import com.slack.api.Slack;
import com.slack.api.methods.impl.MethodsClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassConfig {

    @Bean
    public MethodsClientImpl getMethodsClientImpl() {
        return new MethodsClientImpl(new Slack().getHttpClient());
    }
}
