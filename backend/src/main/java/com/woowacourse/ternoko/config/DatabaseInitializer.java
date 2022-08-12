package com.woowacourse.ternoko.config;

import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTime;
import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeRepository;
import com.woowacourse.ternoko.availabledatetime.domain.AvailableDateTimeStatus;
import com.woowacourse.ternoko.domain.member.Coach;
import com.woowacourse.ternoko.domain.member.Crew;
import com.woowacourse.ternoko.repository.CoachRepository;
import com.woowacourse.ternoko.repository.CrewRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile({"local", "dev"})
@Component
@RequiredArgsConstructor
public class DatabaseInitializer {
    private static final String KST = "Asia/Seoul";
    private static final String TEST_EMAIL = "@woowahan.com";

    private final InitService initService;

    @PostConstruct
    public void init() {
        setTimeZone(TimeZone.getTimeZone(KST));
        initService.dbInit();
    }

    private void setTimeZone(final TimeZone timeZone) {
        TimeZone.setDefault(timeZone);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final CoachRepository coachRepository;
        private final CrewRepository crewRepository;
        private final AvailableDateTimeRepository availableDateTimeRepository;

        public void dbInit() {
            ArrayList<Coach> coaches = new ArrayList<>(12);
            Coach coach1 = new Coach(1L, "이름", "공원", "공원" + TEST_EMAIL,
                    "U1234567890",
                    "https://user-images.githubusercontent.com/26570275/177680173-9bb25eac-5922-407b-889b-bb49ac392c2a.png",
                    "안녕하세요.");
            coaches.add(coach1);
            coaches.add(new Coach(2L, "이름", "네오", "네오" + TEST_EMAIL,
                    "U1234567891",
                    "https://user-images.githubusercontent.com/26570275/177680191-a4497404-c4cb-4f86-8c2c-f4f9c70bcaf7.png",
                    "안녕하세요."));
            coaches.add(new Coach(3L, "이름", "브라운", "브라운" + TEST_EMAIL,
                    "U1234567892",
                    "https://user-images.githubusercontent.com/26570275/177680196-744300be-eb1b-4331-a74f-fdc41ff869d0.png",
                    "안녕하세요."));
            coaches.add(new Coach(4L, "이름", "브리", "브리" + TEST_EMAIL,
                    "U1234567893",
                    "https://user-images.githubusercontent.com/43205258/177765431-63d39896-c8e1-42e8-a229-08309849f2ff.png",
                    "안녕하세요."));
            coaches.add(new Coach(5L, "이름", "왼손", "왼손" + TEST_EMAIL,
                    "U1234567894",
                    "https://user-images.githubusercontent.com/26570275/177680204-6d12cae9-f038-4503-b9de-a75f4b4de456.png",
                    "안녕하세요."));
            coaches.add(new Coach(6L, "이름", "워니", "워니" + TEST_EMAIL,
                    "U1234567895",
                    "https://user-images.githubusercontent.com/26570275/177680207-896f887b-5baf-47a5-b7ea-3b8c1bb5b8c3.png",
                    "안녕하세요."));
            coaches.add(new Coach(7L, "이름", "제이슨", "제이슨" + TEST_EMAIL,
                    "U1234567896",
                    "https://user-images.githubusercontent.com/43205258/177760748-5e0e9efd-d063-4639-9a6d-f48e3a76f919.png",
                    "안녕하세요."));
            coaches.add(new Coach(8L, "이름", "준", "준" + TEST_EMAIL,
                    "U1234567897",
                    "https://user-images.githubusercontent.com/26570275/177680212-63242449-1059-450b-96d8-a06b01a1aea5.png",
                    "안녕하세요."));
            coaches.add(new Coach(9L, "이름", "토미", "토미" + TEST_EMAIL,
                    "U1234567898",
                    "https://user-images.githubusercontent.com/26570275/177680217-764aa425-72cb-4b04-adeb-f110121cdf60.png",
                    "안녕하세요."));
            coaches.add(new Coach(10L, "이름", "포비", "포비" + TEST_EMAIL,
                    "U1234567899",
                    "https://user-images.githubusercontent.com/26570275/177680220-fd4258be-d317-4080-a6f6-eaaa58beef0e.png",
                    "안녕하세요."));
            coaches.add(new Coach(11L, "이름", "구구", "구구" + TEST_EMAIL,
                    "U12345678910",
                    "https://user-images.githubusercontent.com/43205258/177739511-d427cb0c-5a8d-4bfb-9df1-945b4d32afb4.png",
                    "안녕하세요."));
            coaches.add(new Coach(12L, "이름", "포코", "포코" + TEST_EMAIL,
                    "U12345678911",
                    "https://user-images.githubusercontent.com/54317630/177786158-226652b7-7b4a-462c-af3b-775811756c87.png",
                    "안녕하세요."));

            coaches.add(new Coach(13L, "김상록", "록바", "evertree6031@gmail.com",
                    "U02UU3F2H6K",
                    "https://user-images.githubusercontent.com/26570275/182808926-e5563d40-780d-428a-9d9f-57b9318bbefe.png",
                    "이두로 코딩하는 개발자"));

            coachRepository.saveAll(coaches);
            crewRepository.save(new Crew(13L, "사현빈", "바니", "shb1833@gmail.com", "U03N6FKQEJX",
                    "https://a.slack-edge.com/80588/img/avatars-teams/ava_0012-230.png"));

            availableDateTimeRepository.save(new AvailableDateTime(1L, coach1,
                    LocalDateTime.of(LocalDate.of(2022, 8, 25), LocalTime.of(11, 0)), AvailableDateTimeStatus.OPEN));
            availableDateTimeRepository.save(new AvailableDateTime(2L, coach1,
                    LocalDateTime.of(LocalDate.of(2022, 8, 25), LocalTime.of(13, 0)), AvailableDateTimeStatus.OPEN));
        }
    }
}
