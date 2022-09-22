package com.woowacourse.ternoko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TernokoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TernokoApplication.class, args);
    }

}
