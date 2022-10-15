package com.woowacourse.ternoko.config;

import com.woowacourse.ternoko.core.application.InterviewService;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeRepository;
import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTimeStatus;
import com.woowacourse.ternoko.core.domain.comment.CommentRepository;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.InterviewRepository;
import com.woowacourse.ternoko.core.domain.interview.InterviewStatusType;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import com.woowacourse.ternoko.core.domain.member.coach.Coach;
import com.woowacourse.ternoko.core.domain.member.coach.CoachRepository;
import com.woowacourse.ternoko.core.domain.member.crew.Crew;
import com.woowacourse.ternoko.core.domain.member.crew.CrewRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile({"local", "demo"})
@Component
@RequiredArgsConstructor
public class DemoDatabaseInitializer {
    private static final String KST = "Asia/Seoul";
    private static final String TEST_EMAIL = "@test.com";
    private static final String COACH_EMAIL = "@woowahan.com";

    private final DemoDatabaseInitializer.InitService initService;

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
        private final InterviewRepository interviewRepository;
        private final CommentRepository commentRepository;
        private final InterviewService interviewService;

        public void dbInit() {
            final ArrayList<Coach> coaches = new ArrayList<>();
            final ArrayList<Crew> crews = new ArrayList<>();

            // 1 ~ 10 더미 코치 생성
            addDefaultCoaches(coaches);

            long currentMemberId = 11;
            // 11 ~ 110 미리 회원가입 되어있는 코치 생성
            while (currentMemberId <= 110) {
                coaches.add(new Coach(currentMemberId, "코치" + currentMemberId, "코치" + currentMemberId,
                        "email" + currentMemberId + TEST_EMAIL,
                        "U1234567890" + currentMemberId,
                        "https://user-images.githubusercontent.com/54317630/184493934-9a2ba1bb-6051-4428-bb6a-5527c4f480d9.JPG",
                        "안녕하세요." + currentMemberId));
                currentMemberId++;
            }

            // 111 ~ 210 미리 회원가입 되어있는 크루 생성
            while (currentMemberId <= 210) {
                crews.add(new Crew(currentMemberId, "크루이름" + currentMemberId, "크루" + currentMemberId,
                        "이메일" + currentMemberId,
                        "U1234567890" + currentMemberId,
                        "https://user-images.githubusercontent.com/54317630/184493934-9a2ba1bb-6051-4428-bb6a-5527c4f480d9.JPG"));
                currentMemberId++;
            }

            // 크루, 코치 데이터 저장
            coachRepository.saveAll(coaches);
            crewRepository.saveAll(crews);

            final ArrayList<AvailableDateTime> availableDateTimes = new ArrayList<>();
            // 더미 코치의 면담가능시간 생성
            for (long coachId = 1; coachId < 10; coachId++) {
                for (int month = 10; month <= 11; month++) {
                    for (int day = 21; day <= 30; day++) {
                        for (int hour = 10; hour <= 17; hour++) {
                            availableDateTimes.add(new AvailableDateTime(coachId,
                                    LocalDateTime.of(LocalDate.of(2022, month, day), LocalTime.of(hour, 0)),
                                    AvailableDateTimeStatus.OPEN));
                        }
                    }
                }
            }

            // 회원가입된 코치의 면담 가능시간 생성 10/17(1), 10/25(4), 11/5(4)
            for (long coachId = 11; coachId <= 110; coachId++) {
                availableDateTimes.add(new AvailableDateTime(coachId,
                        LocalDateTime.of(LocalDate.of(2022, 10, 14), LocalTime.of(10, 0)),
                        AvailableDateTimeStatus.OPEN));
                for (int i = 0; i < 4; i++) {
                    availableDateTimes.add(new AvailableDateTime(coachId,
                            LocalDateTime.of(LocalDate.of(2022, 10, 25), LocalTime.of(10 + i, 0)),
                            AvailableDateTimeStatus.OPEN));
                }
                for (int i = 0; i < 4; i++) {
                    availableDateTimes.add(new AvailableDateTime(coachId,
                            LocalDateTime.of(LocalDate.of(2022, 11, 5), LocalTime.of(10 + i, 0)),
                            AvailableDateTimeStatus.OPEN));
                }
            }
            availableDateTimeRepository.saveAll(availableDateTimes);

            // 모든 크루에 대해 (크루아이디 - 1000) 코치에게 인터뷰신청
            for (int crewIndex = 0; crewIndex <= 99; crewIndex++) {
                final Long crewId = crews.get(crewIndex).getId();
                final Long coachId = crewId - 100;
                final int coachIndex = crewIndex + 10;

                final List<AvailableDateTime> times = availableDateTimeRepository.findAllByCoachId(coachId);
                for (AvailableDateTime time : times) {
                    final Interview interview = interviewRepository.save(
                            new Interview(time, time.getLocalDateTime(), time.getLocalDateTime().plusMinutes(30),
                                    coaches.get(coachIndex), crews.get(crewIndex), List.of(
                                    FormItem.of("이번 면담을 통해 논의하고 싶은 내용", "[임시데이터] 잘 둘러보다 가세요 ㅎㅎ"),
                                    FormItem.of("이번 면담을 통해 논의하고 싶은 내용", "[임시데이터] 좋은하루 되세요 ㅎㅎ"),
                                    FormItem.of("이번 면담을 통해 논의하고 싶은 내용", "[임시데이터] 터놓고 화이팅!")
                            )));
                    if (interview.getAvailableDateTime().getLocalDateTime().equals(LocalDateTime.of(2022, 10, 14, 10, 0))) {
                        interview.updateStatus(InterviewStatusType.FIXED);
                    }
                }
            }
        }

        private void addDefaultCoaches(ArrayList<Coach> coaches) {
            coaches.add(new Coach(1L, "터놓고", "터노코", "ternoko.official@gmail.com",
                    "U03U8ETQ48Y",
                    "https://user-images.githubusercontent.com/54317630/184493934-9a2ba1bb-6051-4428-bb6a-5527c4f480d9.JPG",
                    "면담은 터놓고 하세요."));

            coaches.add(new Coach(2L, "이름", "네오", "네오" + COACH_EMAIL,
                    "U1234567891",
                    "https://user-images.githubusercontent.com/26570275/177680191-a4497404-c4cb-4f86-8c2c-f4f9c70bcaf7.png",
                    "안녕하세요."));
            coaches.add(new Coach(3L, "이름", "브라운", "브라운" + COACH_EMAIL,
                    "U1234567892",
                    "https://user-images.githubusercontent.com/26570275/177680196-744300be-eb1b-4331-a74f-fdc41ff869d0.png",
                    "안녕하세요."));
            coaches.add(new Coach(4L, "이름", "브리", "브리" + COACH_EMAIL,
                    "U1234567893",
                    "https://user-images.githubusercontent.com/43205258/177765431-63d39896-c8e1-42e8-a229-08309849f2ff.png",
                    "안녕하세요."));
            coaches.add(new Coach(5L, "이름", "왼손", "왼손" + COACH_EMAIL,
                    "U1234567894",
                    "https://user-images.githubusercontent.com/26570275/177680204-6d12cae9-f038-4503-b9de-a75f4b4de456.png",
                    "안녕하세요."));
            coaches.add(new Coach(6L, "이름", "워니", "워니" + COACH_EMAIL,
                    "U1234567895",
                    "https://user-images.githubusercontent.com/26570275/177680207-896f887b-5baf-47a5-b7ea-3b8c1bb5b8c3.png",
                    "안녕하세요."));
            coaches.add(new Coach(7L, "이름", "제이슨", "제이슨" + COACH_EMAIL,
                    "U1234567896",
                    "https://user-images.githubusercontent.com/43205258/177760748-5e0e9efd-d063-4639-9a6d-f48e3a76f919.png",
                    "안녕하세요."));
            coaches.add(new Coach(8L, "이름", "준", "준" + COACH_EMAIL,
                    "U1234567897",
                    "https://user-images.githubusercontent.com/26570275/177680212-63242449-1059-450b-96d8-a06b01a1aea5.png",
                    "안녕하세요."));
            coaches.add(new Coach(9L, "이름", "토미", "토미" + COACH_EMAIL,
                    "U1234567898",
                    "https://user-images.githubusercontent.com/26570275/177680217-764aa425-72cb-4b04-adeb-f110121cdf60.png",
                    "안녕하세요."));
            coaches.add(new Coach(10L, "이름", "공원", "공원" + COACH_EMAIL,
                    "U1234567890",
                    "https://user-images.githubusercontent.com/26570275/177680173-9bb25eac-5922-407b-889b-bb49ac392c2a.png",
                    "안녕하세요."));
        }
    }
}
