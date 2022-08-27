package com.woowacourse.ternoko.support.fixture;

import com.woowacourse.ternoko.interview.domain.formitem.Answer;
import com.woowacourse.ternoko.interview.domain.formitem.FormItem;
import com.woowacourse.ternoko.interview.domain.formitem.Question;
import com.woowacourse.ternoko.interview.dto.FormItemRequest;
import com.woowacourse.ternoko.interview.dto.InterviewRequest;
import java.time.LocalDateTime;
import java.util.List;

public class InterviewFixture {

    public static final Long INTERVIEW_TIME = 30L;

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

    private static FormItem createFormItem(int count) {
        return new FormItem(null, Question.from("고정질문" + count), Answer.from("고정답변" + count));
    }

    public static final InterviewRequest COACH1_INTERVIEW_REQUEST1 = new InterviewRequest(MemberFixture.COACH1.getId(),
            CoachAvailableTimeFixture.NOW_PLUS_2_DAYS_FIRST_TIME,
            FORM_ITEM_REQUESTS);

    public static final InterviewRequest COACH1_INTERVIEW_REQUEST2 = new InterviewRequest(MemberFixture.COACH1.getId(),
            CoachAvailableTimeFixture.NOW_PLUS_2_DAYS_SECOND_TIME,
            FORM_ITEM_REQUESTS);
}