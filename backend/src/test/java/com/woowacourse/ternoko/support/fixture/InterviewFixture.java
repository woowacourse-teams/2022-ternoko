package com.woowacourse.ternoko.support.fixture;

import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.AVAILABLE_DATE_TIME;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.AVAILABLE_DATE_TIME_COACH1_1;
import static com.woowacourse.ternoko.support.fixture.CoachAvailableTimeFixture.AVAILABLE_DATE_TIME_COACH1_2;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.COACH1;
import static com.woowacourse.ternoko.support.fixture.MemberFixture.CREW1;

import com.woowacourse.ternoko.core.domain.availabledatetime.AvailableDateTime;
import com.woowacourse.ternoko.core.domain.interview.Interview;
import com.woowacourse.ternoko.core.domain.interview.InterviewStatusType;
import com.woowacourse.ternoko.core.domain.interview.formitem.Answer;
import com.woowacourse.ternoko.core.domain.interview.formitem.FormItem;
import com.woowacourse.ternoko.core.domain.interview.formitem.Question;
import com.woowacourse.ternoko.core.dto.request.FormItemRequest;
import com.woowacourse.ternoko.core.dto.request.InterviewRequest;
import java.time.LocalDateTime;
import java.util.List;

public class InterviewFixture {

    public static final Long INTERVIEW_TIME = 30L;

    public static final Interview INTERVIEW = new Interview(1L,
            AVAILABLE_DATE_TIME,
            LocalDateTime.now().plusDays(10),
            LocalDateTime.now().minusDays(2),
            COACH1, CREW1, List.of(createFormItem(1),
            createFormItem(2),
            createFormItem(3)), InterviewStatusType.FIXED);

    private static final LocalDateTime NOW = LocalDateTime.now().withNano(0).plusDays(2);
    public static final LocalDateTime AFTER_TWO_DAYS = LocalDateTime.of(NOW.getYear(), NOW.getMonthValue(),
            NOW.getDayOfMonth(), NOW.getHour(), NOW.getMinute());

    public static final List<FormItemRequest> FORM_ITEM_REQUESTS = List.of(new FormItemRequest("고정질문1", "답변1"),
            new FormItemRequest("고정질문2", "답변2"),
            new FormItemRequest("고정질문3", "답변3"));

    public static final List<FormItemRequest> FORM_ITEM_UPDATE_REQUESTS = List.of(new FormItemRequest("수정질문1", "수정답변1"),
            new FormItemRequest("수정질문2", "수정답변2"),
            new FormItemRequest("수정질문3", "수정답변3"));


    public static final List<FormItem> FORM_ITEMS1 = List.of(createFormItem(1),
            createFormItem(2),
            createFormItem(3));

    public static final List<FormItem> FORM_ITEMS2 = List.of(createFormItem(4),
            createFormItem(5),
            createFormItem(6));

    private static FormItem createFormItem(int number) {
        return new FormItem(null, Question.from("고정질문" + number), Answer.from("고정답변" + number));
    }

    public static final InterviewRequest COACH1_INTERVIEW_REQUEST1 = createInterviewRequest(AVAILABLE_DATE_TIME_COACH1_1);
    public static final InterviewRequest COACH1_INTERVIEW_REQUEST2 = createInterviewRequest(AVAILABLE_DATE_TIME_COACH1_2);

    private static InterviewRequest createInterviewRequest(final AvailableDateTime availableDateTime) {
        return new InterviewRequest(COACH1.getId(),
                availableDateTime.getId(),
                availableDateTime.getLocalDateTime(),
                FORM_ITEM_REQUESTS);
    }
}
