package com.woowacourse.ternoko.config;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.repository.CoachRepository;

import lombok.RequiredArgsConstructor;

@Profile({"local", "dev"})
@Component
@RequiredArgsConstructor
public class DatabaseInitializer {
    private static final String TEST_EMAIL = "test@email.com";

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final CoachRepository coachRepository;

        public void dbInit() {
            ArrayList<Coach> coaches = new ArrayList<>(12);
            coaches.add(new Coach(1L, "공원", TEST_EMAIL,
                "https://user-images.githubusercontent.com/26570275/177680173-9bb25eac-5922-407b-889b-bb49ac392c2a.png"));
            coaches.add(new Coach(2L, "네오", TEST_EMAIL,
                "https://user-images.githubusercontent.com/26570275/177680191-a4497404-c4cb-4f86-8c2c-f4f9c70bcaf7.png"));
            coaches.add(new Coach(3L, "브라운", TEST_EMAIL,
                "https://user-images.githubusercontent.com/26570275/177680196-744300be-eb1b-4331-a74f-fdc41ff869d0.png"));
            coaches.add(new Coach(4L, "브리", TEST_EMAIL,
                "https://user-images.githubusercontent.com/43205258/177765431-63d39896-c8e1-42e8-a229-08309849f2ff.png"));
            coaches.add(new Coach(5L, "왼손", TEST_EMAIL,
                "https://user-images.githubusercontent.com/26570275/177680204-6d12cae9-f038-4503-b9de-a75f4b4de456.png"));
            coaches.add(new Coach(6L, "워니", TEST_EMAIL,
                "https://user-images.githubusercontent.com/26570275/177680207-896f887b-5baf-47a5-b7ea-3b8c1bb5b8c3.png"));
            coaches.add(new Coach(7L, "제이슨", TEST_EMAIL,
                "https://user-images.githubusercontent.com/43205258/177760748-5e0e9efd-d063-4639-9a6d-f48e3a76f919.png"));
            coaches.add(new Coach(8L, "준", TEST_EMAIL,
                "https://user-images.githubusercontent.com/26570275/177680212-63242449-1059-450b-96d8-a06b01a1aea5.png"));
            coaches.add(new Coach(9L, "토미", TEST_EMAIL,
                "https://user-images.githubusercontent.com/26570275/177680217-764aa425-72cb-4b04-adeb-f110121cdf60.png"));
            coaches.add(new Coach(10L, "포비", TEST_EMAIL,
                "https://user-images.githubusercontent.com/26570275/177680220-fd4258be-d317-4080-a6f6-eaaa58beef0e.png"));
            coaches.add(new Coach(11L, "구구", TEST_EMAIL,
                "https://user-images.githubusercontent.com/43205258/177739511-d427cb0c-5a8d-4bfb-9df1-945b4d32afb4.png"));
            coaches.add(new Coach(12L, "포코", TEST_EMAIL,
                "https://user-images.githubusercontent.com/54317630/177786158-226652b7-7b4a-462c-af3b-775811756c87.png"));
            coachRepository.saveAll(coaches);
        }
    }
}
